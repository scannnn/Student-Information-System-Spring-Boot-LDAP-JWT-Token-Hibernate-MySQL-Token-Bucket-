package com.araproje.OgrenciBilgiSistemi.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.araproje.OgrenciBilgiSistemi.model.Course;
import com.araproje.OgrenciBilgiSistemi.model.Section;
import com.araproje.OgrenciBilgiSistemi.model.SectionClassroom;
import com.araproje.OgrenciBilgiSistemi.model.Student;
import com.araproje.OgrenciBilgiSistemi.model.StudentSection;
import com.araproje.OgrenciBilgiSistemi.service.ClassroomService;
import com.araproje.OgrenciBilgiSistemi.service.CourseService;
import com.araproje.OgrenciBilgiSistemi.service.InstructorService;
import com.araproje.OgrenciBilgiSistemi.service.SectionClassroomService;
import com.araproje.OgrenciBilgiSistemi.service.SectionService;
import com.araproje.OgrenciBilgiSistemi.service.StudentSectionService;

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
	@Autowired
	StudentSectionService studentSectionService;
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
						Integer startTime = Integer.parseInt(oneSectionDay.get("startTime").split(":")[0]);
						Integer finishTime = Integer.parseInt(oneSectionDay.get("finishTime").split(":")[0]);
						if(startTime == finishTime){
							throw new Exception("Başlangıç ve bitiş saatleri aynı olamaz.");
						}
						else {
							for(SectionClassroom oneSectionClassroom : sectionClassrooms) {
								
								if( (oneSectionClassroom.getClassroom().getClassroomCode().equalsIgnoreCase(oneSectionDay.get("classroomCode"))) && 
										(oneSectionClassroom.getDay().equalsIgnoreCase(oneSectionDay.get("day"))) && 
										(oneSectionClassroom.getSection().getYear().equalsIgnoreCase((String)JSON.get("year"))) &&
										(oneSectionClassroom.getSection().getTerm().equalsIgnoreCase((String)JSON.get("term"))) ) {
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
										(oneSectionClassroom.getDay().equalsIgnoreCase(oneSectionDay.get("day"))) && 
										(oneSectionClassroom.getSection().getYear().equalsIgnoreCase((String)JSON.get("year"))) &&
										(oneSectionClassroom.getSection().getTerm().equalsIgnoreCase((String)JSON.get("term"))) ) {
									Integer startTimeTemp = Integer.parseInt(oneSectionClassroom.getStartTime().split(":")[0]);
									Integer finishTimeTemp = Integer.parseInt(oneSectionClassroom.getFinishTime().split(":")[0]);
									if( ( (startTimeTemp>=startTime)&&(startTimeTemp<finishTime) ) ||
											( (finishTimeTemp>startTime)&&(finishTimeTemp<=finishTime) ) ){
										throw new Exception("Gruba ders eklemeye çalıştığınız ders "+oneSectionDay.get("startTime")+"-"+oneSectionDay.get("finishTime")+" aralıklarında"
												+ " eklenememektedir. Nedeni ise eklemeye çalıştığınız öğretmenin "+oneSectionClassroom.getSection().getCourse().getCourseCode()
												+ " "+oneSectionClassroom.getSection().getSectionCode()+" dersinde "+oneSectionClassroom.getDay()+" günü "+oneSectionClassroom.getStartTime()+"-"
												+ oneSectionClassroom.getFinishTime()+" aralıklarında başka bir dersi bulunmasıdır.");
									}
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
					List<SectionClassroom> sectionClassroomsTemp = sectionClassrooms;
					for(SectionClassroom SC : sectionClassroomsTemp) {
						if(SC.getSection().getId() == Integer.parseInt(id)) {
							sectionClassrooms.remove(SC);
						}
					}
					
					for(Map<String, String> oneSectionDay : sectionDays) {
						Integer startTime = Integer.parseInt(oneSectionDay.get("startTime").split(":")[0]);
						Integer finishTime = Integer.parseInt(oneSectionDay.get("finishTime").split(":")[0]);
						if(startTime == finishTime){
							throw new Exception("Başlangıç ve bitiş saatleri aynı olamaz.");
						}
						else {
							for(SectionClassroom oneSectionClassroom : sectionClassrooms) {
								
								if( (oneSectionClassroom.getClassroom().getClassroomCode().equalsIgnoreCase(oneSectionDay.get("classroomCode"))) && 
										(oneSectionClassroom.getDay().equalsIgnoreCase(oneSectionDay.get("day"))) && 
										(oneSectionClassroom.getSection().getYear().equalsIgnoreCase((String)JSON.get("year"))) &&
										(oneSectionClassroom.getSection().getTerm().equalsIgnoreCase((String)JSON.get("term"))) ) {
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
										(oneSectionClassroom.getDay().equalsIgnoreCase(oneSectionDay.get("day"))) && 
										(oneSectionClassroom.getSection().getYear().equalsIgnoreCase((String)JSON.get("year"))) &&
										(oneSectionClassroom.getSection().getTerm().equalsIgnoreCase((String)JSON.get("term"))) ) {
									Integer startTimeTemp = Integer.parseInt(oneSectionClassroom.getStartTime().split(":")[0]);
									Integer finishTimeTemp = Integer.parseInt(oneSectionClassroom.getFinishTime().split(":")[0]);
									if( ( (startTimeTemp>=startTime)&&(startTimeTemp<finishTime) ) ||
											( (finishTimeTemp>startTime)&&(finishTimeTemp<=finishTime) ) ){
										throw new Exception("Gruba ders eklemeye çalıştığınız ders "+oneSectionDay.get("startTime")+"-"+oneSectionDay.get("finishTime")+" aralıklarında"
												+ " eklenememektedir. Nedeni ise eklemeye çalıştığınız öğretmenin "+oneSectionClassroom.getSection().getCourse().getCourseCode()
												+ " "+oneSectionClassroom.getSection().getSectionCode()+" dersinde "+oneSectionClassroom.getDay()+" günü "+oneSectionClassroom.getStartTime()+"-"
												+ oneSectionClassroom.getFinishTime()+" aralıklarında başka bir dersi bulunmasıdır.");
									}
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
	
	public boolean validateStudentSection(Student student, String sectionId) throws Exception {
		try {
			Section section = sectionService.get(Integer.parseInt(sectionId));
			
			if(!section.isSectionFull()) {
				List<StudentSection> studentSections = studentSectionService.getAll(student);
				List<StudentSection> studentSectionsTemp = studentSections;
				
				for(StudentSection ss : studentSectionsTemp) {
					if(!ss.getSection().getYear().equalsIgnoreCase(section.getYear()) && 
							!ss.getSection().getTerm().equalsIgnoreCase(section.getTerm()) ) {
						studentSections.remove(ss);
					}
				}
				
				// ÖN KOŞULA BAKMA
				
				for(StudentSection ss : studentSections) {
					if(ss.getSection().getCourse().getId() == section.getCourse().getId()) {
						throw new Exception(ss.getSection().getCourse().getCourseCode()+" dersini hali hazırda "
								+ ss.getSection().getSectionCode()+" grubundan almış bulunmaktasınız. Bir dersin "
										+ "yalnızca 1 grubundan ders seçebilirsiniz.");
					}
				}
				
				for(SectionClassroom sc : section.getSecClassrooms()) {
					Integer startTime = Integer.parseInt(sc.getStartTime().split(":")[0]);
					Integer finishTime = Integer.parseInt(sc.getFinishTime().split(":")[0]);
					for(StudentSection ss : studentSections) {
						for(SectionClassroom sctemp : ss.getSection().getSecClassrooms()) {
							if(sc.getDay().equalsIgnoreCase(sctemp.getDay())) {
								Integer startTimeTemp = Integer.parseInt(sctemp.getStartTime().split(":")[0]);
								Integer finishTimeTemp = Integer.parseInt(sctemp.getFinishTime().split(":")[0]);
								if( ( (startTimeTemp>=startTime)&&(startTimeTemp<finishTime) ) ||
										( (finishTimeTemp>startTime)&&(finishTimeTemp<=finishTime) ) ){
									throw new Exception("Eklemeye çalıştığınız ders grubu, almış olduğunuz "+ ss.getSection().getCourse().getCourseCode()
											+" "+ss.getSection().getSectionCode()+" ders ile çakışmaktadır. Lütfen Takvim sekmesinden derslerin saatlerini"
											+" dikkate alarak ekleme yapınız.");
								}
							}
						}
					}
				}
			}
			else throw new Exception("Üzgünüz, eklemeye çalıştığınız dersin kontenjanı dolmuştur.");
			
		}
		catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
		return true;
	}
}
