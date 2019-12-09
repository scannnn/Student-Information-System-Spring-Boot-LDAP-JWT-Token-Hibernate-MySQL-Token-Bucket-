package com.araproje.OgrenciBilgiSistemi.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.araproje.OgrenciBilgiSistemi.model.Course;
import com.araproje.OgrenciBilgiSistemi.model.SectionClassroom;
import com.araproje.OgrenciBilgiSistemi.service.ClassroomService;
import com.araproje.OgrenciBilgiSistemi.service.CourseService;
import com.araproje.OgrenciBilgiSistemi.service.InstructorService;
import com.araproje.OgrenciBilgiSistemi.service.SectionClassroomService;
import com.araproje.OgrenciBilgiSistemi.service.SectionService;

@Service
public class ValidateMethods {

	@Autowired
	SectionService sectionService;
	@Autowired
	ClassroomService classroomService;
	@Autowired
	CourseService courseService;
	@Autowired
	InstructorService instructorService;
	@Autowired
	SectionClassroomService sectionClassroomService;
	DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	@SuppressWarnings("unchecked")
	public boolean validateSection(Map<String, Object> JSON) throws Exception {
		try {
			Course course = courseService.get((String)JSON.get("courseCode"));
			List<Map<String, String>> sectionDays = (List<Map<String, String>>)JSON.get("sectionClassrooms");
			if(sectionDays != null) {
				if(!sectionService.isExist(course, (String)JSON.get("sectionCode"))) {
					List<SectionClassroom> sectionClassrooms = sectionClassroomService.getAll();
					
					for(Map<String, String> oneSectionDay : sectionDays) {
						Integer startTime = Integer.parseInt(oneSectionDay.get("startDate").split(":")[0]);
						Integer finishTime = Integer.parseInt(oneSectionDay.get("finishDate").split(":")[0]);
						
						for(SectionClassroom oneSectionClassroom : sectionClassrooms) {
							
							if( (oneSectionClassroom.getClassroom().getClassroomCode().equalsIgnoreCase(oneSectionDay.get("classroomCode"))) && 
									(oneSectionClassroom.getDay().equalsIgnoreCase(oneSectionDay.get("day"))) ) {
								Integer startTimeTemp = Integer.parseInt(oneSectionClassroom.getStartTime().split(":")[0]);
								Integer finishTimeTemp = Integer.parseInt(oneSectionClassroom.getFinishTime().split(":")[0]);
								
								if( ( (startTimeTemp>=startTime)&&(startTimeTemp<finishTime) ) ||
										( (finishTimeTemp>startTime)&&(finishTimeTemp<=finishTime) ) ){
									throw new Exception("Gruba ders eklemeye çalıştığınız "+oneSectionDay.get("classroomCode")+" sınıfında aynı saat araklıklarına zaten farklı bir ders mevcut. "
											+ "Bu ders: "+oneSectionClassroom.getSection().getCourse().getCourseCode()+ " "+oneSectionClassroom.getSection().getSectionCode()
											+ " "+oneSectionClassroom.getDay()+" günü"+" Başlangıç Saati: "+oneSectionClassroom.getStartTime()+" Bitiş Saati: "+oneSectionClassroom.getFinishTime());
								}
							}
							
							if((oneSectionClassroom.getSection().getInstructor().getInstructorCode().equalsIgnoreCase((String)JSON.get("instructorCode"))) && 
									(oneSectionClassroom.getDay().equalsIgnoreCase(oneSectionDay.get("day"))) ) {
								Integer startTimeTemp = Integer.parseInt(oneSectionClassroom.getStartTime().split(":")[0]);
								Integer finishTimeTemp = Integer.parseInt(oneSectionClassroom.getFinishTime().split(":")[0]);
								if( ( (startTimeTemp>=startTime)&&(startTimeTemp<finishTime) ) ||
										( (finishTimeTemp>startTime)&&(finishTimeTemp<=finishTime) ) ){
									throw new Exception("Gruba ders eklemeye çalıştığınız ders "+oneSectionDay.get("startDate")+"-"+oneSectionDay.get("finishDate")+" aralıklarında"
											+ " eklenememektedir. Nedeni ise eklemeye çalıştığınız öğretmenin "+oneSectionClassroom.getSection().getCourse().getCourseCode()
											+ " "+oneSectionClassroom.getSection().getSectionCode()+" dersinde "+oneSectionClassroom.getDay()+" günü "+oneSectionClassroom.getStartTime()+"-"
											+ oneSectionClassroom.getFinishTime()+" aralıklarında başka bir dersi bulunmasıdır.");
								}
							}
						}
					}
					
				}
				else throw new Exception("Eklemeye çalıştığınız grup hali hazırda mevcut.");
			}
			else throw new Exception("Grubun açılabilmesi için en azından 1 güne ders eklemeniz gerekmektedir.");
		}
		catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public boolean validateSectionUpdate(String id, Map<String, Object> JSON) throws Exception {
		try {
			List<Map<String, String>> sectionDays = (List<Map<String, String>>)JSON.get("sectionClassrooms");
			if(sectionDays != null) {
				if(sectionService.isExist(Integer.parseInt(id))) {
					List<SectionClassroom> sectionClassrooms = sectionClassroomService.getAll();
					
					for(SectionClassroom SC : sectionClassrooms) {
						if(SC.getSection().getId() == Integer.parseInt(id)) {
							sectionClassrooms.remove(SC);
						}
					}
					
					for(Map<String, String> oneSectionDay : sectionDays) {
						Integer startTime = Integer.parseInt(oneSectionDay.get("startDate").split(":")[0]);
						Integer finishTime = Integer.parseInt(oneSectionDay.get("finishDate").split(":")[0]);
						
						for(SectionClassroom oneSectionClassroom : sectionClassrooms) {
							
							if( (oneSectionClassroom.getClassroom().getClassroomCode().equalsIgnoreCase(oneSectionDay.get("classroomCode"))) && 
									(oneSectionClassroom.getDay().equalsIgnoreCase(oneSectionDay.get("day"))) ) {
								Integer startTimeTemp = Integer.parseInt(oneSectionClassroom.getStartTime().split(":")[0]);
								Integer finishTimeTemp = Integer.parseInt(oneSectionClassroom.getFinishTime().split(":")[0]);
								
								if( ( (startTimeTemp>=startTime)&&(startTimeTemp<finishTime) ) ||
										( (finishTimeTemp>startTime)&&(finishTimeTemp<=finishTime) ) ){
									throw new Exception("Gruba ders eklemeye çalıştığınız "+oneSectionDay.get("classroomCode")+" sınıfında aynı saat araklıklarına zaten farklı bir ders mevcut. "
											+ "Bu ders: "+oneSectionClassroom.getSection().getCourse().getCourseCode()+ " "+oneSectionClassroom.getSection().getSectionCode()
											+ " "+oneSectionClassroom.getDay()+" günü"+" Başlangıç Saati: "+oneSectionClassroom.getStartTime()+" Bitiş Saati: "+oneSectionClassroom.getFinishTime());
								}
							}
							
							if((oneSectionClassroom.getSection().getInstructor().getInstructorCode().equalsIgnoreCase((String)JSON.get("instructorCode"))) && 
									(oneSectionClassroom.getDay().equalsIgnoreCase(oneSectionDay.get("day"))) ) {
								Integer startTimeTemp = Integer.parseInt(oneSectionClassroom.getStartTime().split(":")[0]);
								Integer finishTimeTemp = Integer.parseInt(oneSectionClassroom.getFinishTime().split(":")[0]);
								if( ( (startTimeTemp>=startTime)&&(startTimeTemp<finishTime) ) ||
										( (finishTimeTemp>startTime)&&(finishTimeTemp<=finishTime) ) ){
									throw new Exception("Gruba ders eklemeye çalıştığınız ders "+oneSectionDay.get("startDate")+"-"+oneSectionDay.get("finishDate")+" aralıklarında"
											+ " eklenememektedir. Nedeni ise eklemeye çalıştığınız öğretmenin "+oneSectionClassroom.getSection().getCourse().getCourseCode()
											+ " "+oneSectionClassroom.getSection().getSectionCode()+" dersinde "+oneSectionClassroom.getDay()+" günü "+oneSectionClassroom.getStartTime()+"-"
											+ oneSectionClassroom.getFinishTime()+" aralıklarında başka bir dersi bulunmasıdır.");
								}
							}
						}
					}
					
				}
				else throw new Exception("Eklemeye çalıştığınız dersin grubu bulunmamaktadır.");
			}
			else throw new Exception("Grubun açılabilmesi için en azından 1 güne ders eklemeniz gerekmektedir.");
		}
		catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return true;
	}
}
