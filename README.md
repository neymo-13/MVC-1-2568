# ระบบลงทะเบียนเรียน

## รายละเอียด

โปรเจคนี้เป็นระบบจัดการการลงทะเบียนเรียนของนักเรียน ที่พัฒนาขึ้นตามแนวคิด MVC (Model-View-Controller) Design Pattern ด้วยภาษา Java ระบบถูกออกแบบมาเพื่อจัดการข้อมูลนักเรียน, รายวิชา, โครงสร้างหลักสูตร, และการลงทะเบียนเรียน พร้อมทั้งมีระบบยืนยันตัวตนเบื้องต้นเพื่อแยกระหว่างผู้ใช้งานที่เป็น นักเรียน (Student) และ ผู้ดูแลระบบ (Admin) โดยใช้ไฟล์ CSV เป็นฐานข้อมูลจำลอง

## คุณสมบัติหลัก
### 1.สำหรับผู้ดูแลระบบ (Admin)
	- เข้าสู่ระบบ: ยืนยันตัวตนเพื่อเข้าถึงฟังก์ชันของผู้ดูแลระบบ
	- จัดการข้อมูลนักเรียน:
		- แสดงรายชื่อนักเรียนทั้งหมดในระบบ
		- กรองนักเรียนตามโรงเรียน
		- เรียงลำดับข้อมูลนักเรียนตามชื่อ
	- ดูประวัตินักเรียน: สามารถคลิกเพื่อดูข้อมูลและรายวิชาที่ลงทะเบียนของนักเรียนแต่ละคนได้
	- จัดการเกรด:
		- เข้าสู่หน้ากรอกเกรดสำหรับแต่ละรายวิชา
		- แสดงรายชื่อนักเรียนที่ลงทะเบียนในวิชานั้นๆ
		- กรอกและบันทึกเกรด (A, B+, B, C+, C, D+, D, F) ให้กับนักเรียน
### 2.🎓 สำหรับนักเรียน (Student)
	-เข้าสู่ระบบ: ยืนยันตัวตนด้วยรหัสนักเรียนเพื่อเข้าสู่ระบบ
	- ดูข้อมูลส่วนตัว: แสดงหน้าประวัติส่วนตัว, รายละเอียด, และรายวิชาทั้งหมดที่เคยลงทะเบียนพร้อมเกรดที่ได้รับ
	- ลงทะเบียนเรียน:
		- แสดงรายวิชาในหลักสูตรที่ยังไม่ได้ลงทะเบียน
		- ระบบจะตรวจสอบวิชาบังคับก่อน (Prerequisite) หากยังไม่ผ่านวิชาบังคับ จะไม่สามารถลงทะเบียนได้
		- เมื่อลงทะเบียนสำเร็จ สามารถกดกลับมายังหน้าประวัติส่วนตัว

## การทำงานในแต่ละส่วน (MVC)

### 1. Model: ส่วนจัดการข้อมูลและตรรกะ
เป็นส่วนที่ควบคุมข้อมูลและกฎเกณฑ์ทางธุรกิจทั้งหมดของโปรแกรม ไม่ยุ่งเกี่ยวกับหน้าตาโปรแกรม

* `Student.java`, `Subject.java`, `SubjectStructure.java`, `RegisteredSubject.java`: เป็นคลาสที่ใช้เก็บโครงสร้างข้อมูล (Data Objects) เช่น ข้อมูลนักเรียน, ข้อมูลรายวิชา เป็นต้น
* `StudentRepository.java`, `SubjectRepository.java`, `RegisteredSubjectRepository.java`, `SubjectStructureRepository`: ทำหน้าที่เป็น "คลังข้อมูล" มีหน้าที่หลักในการ อ่านและเขียนข้อมูลลงไฟล์ CSV เช่น การดึงรายชื่อนักเรียนทั้งหมด, การบันทึกข้อมูลนักเรียนใหม่
* `DataManager.java`: เป็นคลาสที่จัดการ Repository ทั้งหมด เพื่อให้ Controller เรียกใช้งานได้ง่ายในที่เดียว
* `AuthService.java`: จัดการตรรกะเกี่ยวกับการ ยืนยันตัวตน (Authentication) เช่น ตรวจสอบว่ารหัสนักเรียนและรหัสผ่านที่กรอกมาถูกต้องหรือไม่

### 2. View: ส่วนแสดงผลและติดต่อผู้ใช้
เป็นส่วนที่สร้างหน้าจอ (UI) ทั้งหมดที่ผู้ใช้เห็นและโต้ตอบด้วย ทำหน้าที่รับข้อมูลจากผู้ใช้ (เช่น การคลิกปุ่ม, การกรอกข้อความ) แล้วส่งต่อไปให้ Controller
* `LoginView.java`: หน้าจอสำหรับให้ผู้ใช้ (ทั้ง Admin และ Student) กรอกข้อมูลเพื่อเข้าสู่ระบบ
* `AdminDashboardView.java`: หน้าจอหลักสำหรับ Admin แสดงรายชื่อนักเรียนทั้งหมด พร้อมฟังก์ชัน กรอง และเรียงลำดับ
* `StudentDashboardView.java`: หน้าจอหลักสำหรับ นักเรียน แสดงข้อมูลส่วนตัวและรายวิชาที่ลงทะเบียนไปแล้ว
* `CourseRegistrationView.java`: หน้าจอสำหรับให้นักเรียนเลือกลงทะเบียนเรียน
* `GradeEntryView.java`: หน้าจอสำหรับ Admin เพื่อกรอกเกรดให้นักเรียนในแต่ละวิชา

### 3. Controller: ส่วนควบคุมการทำงาน
เป็นตัวกลางที่เชื่อมระหว่าง Model และ View ทำหน้าที่รับคำสั่งจาก View แล้วไปสั่งให้ Model ทำงานตามตรรกะ จากนั้นนำผลลัพธ์ที่ได้จาก Model ไปอัปเดตการแสดงผลใน View
* `AuthController.java`: รับข้อมูลการล็อกอินจาก `LoginView`, ส่งไปให้ `AuthService` ใน Model ตรวจสอบ และตัดสินใจว่าจะให้ผู้ใช้เข้าระบบได้หรือไม่ ถ้าได้จะเปิดหน้าจอ `AdminDashboardView` หรือ `StudentDashboardView` ต่อไป
* `AdminController.java`: ควบคุมการทำงานที่เกี่ยวกับ Admin ทั้งหมด เช่น เมื่อ Admin ทำการกรองนักเรียนตามชื่อโรงเรียนใน `AdminDashboardView`, `Controller` จะสั่งให้ `StudentRepository` ค้นหาข้อมูลตามชื่อโรงเรียนแล้วส่งผลลัพธ์กลับไปแสดงใน View
* `StudentController.java`: ควบคุมการทำงานของฝั่งนักเรียน เช่น เมื่อนักเรียนกดลงทะเบียนวิชาใน `CourseRegistrationView`, Controller จะตรวจสอบเงื่อนไข (เช่น วิชาบังคับก่อน) กับ Model ก่อนจะบันทึกข้อมูล

###  ส่วนสนับสนุน (Utility)
* `CsvUtil.java`: เป็น เครื่องมือเสริม ที่ Repository ต่างๆ เรียกใช้เพื่อทำงานเฉพาะทาง คือการอ่านและเขียนไฟล์ CSV โดยที่ Repository ไม่จำเป็นต้องรู้รายละเอียดของการทำงานกับไฟล์เอง
* `Main.java`: เป็นจุดเริ่มต้นของโปรแกรม ทำหน้าที่สร้างและเปิดหน้าจอแรก (LoginView) เพื่อให้ผู้ใช้เริ่มใช้งานโปรแกรม

## Workflow

### ของผู้ดูแลระบบ (Admin)
1. การเข้าสู่ระบบ (Authentication)
   * Action: Admin เปิดโปรแกรม แสดงหน้า LoginView ทำการกรอก Username(admin)/Password(admin1234) จากนั้นกด “Login”
   * Behind the Scenes:
     	1. LoginView ส่งข้อมูลไปที่ `AuthController`
     	2. `AuthController` เรียก `AuthService` ตรวจสอบความถูกต้อง
     	3. ถ้าเป็น Admin จริง ทำการปิด LoginView และเปิดหน้า AdminDashboardView
   * Result: Admin เข้าสู่หน้าแดชบอร์ดหลัก แสดงรายชื่อนักเรียนทั้งหมด
2. การจัดการข้อมูลนักเรียน
   * Action: บน AdminDashboardView ดูข้อมูลโดยทำการแสดงเรียงตามชื่อ
   * Behind the Scenes:
     	* `AdminDashboardView` ส่ง Action ไป `AdminController`
     	* `AdminController` สั่ง `StudentRepository` ให้กรองข้อมูลจากไฟล์ CSV
     	* ส่งผลลัพธ์ (List<Student>) กลับมา
     	* Controller สั่งให้ View อัปเดตตารางใหม่
   * Result: ตารางนักเรียนแสดงผลตามเงื่อนไข
3. การดูประวัตินักเรียนรายบุคคล (View Profile)
 	* Action: Admin ดับเบิลคลิกที่นักเรียนในตาราง
 	* Behind the Scenes:
  	 	* Controller ดึงข้อมูลนักเรียนจาก `StudentRepository`
   		* ดึงประวัติการลงทะเบียนจาก `RegisteredSubjectRepository`
   		* เปิดหน้าต่างใหม่แสดงข้อมูลนักเรียน
 	* Result: Admin เห็นข้อมูลส่วนตัวและรายวิชาพร้อมเกรดทั้งหมด
4. การกรอกเกรด (Grade Entry)
	* Action: เลือกวิชาจาก Dropdown → กด “แสดงรายชื่อ”
	* Behind the Scenes:
  		* Controller รับรหัสวิชาที่เลือก → ดึงนักเรียนที่ลงทะเบียนจาก `RegisteredSubjectRepository`
  		* เปิดหน้า GradeEntryView พร้อมส่งรายชื่อนักเรียน
	* Result: Admin เห็นรายชื่อนักเรียนพร้อมช่องกรอกเกรด
5. การบันทึกเกรด (Save Grades)
	* Action: กรอกเกรด → กดปุ่ม “บันทึก”
	* Behind the Scenes:
   		* `GradeEntryView` ส่งข้อมูลเกรดไป `AdminController`
			* Controller สั่ง `RegisteredSubjectRepository` อัปเดตไฟล์ CSV
	* Result: ข้อมูลเกรดถูกบันทึกถาวร พร้อมข้อความยืนยัน
     	
### ของนักเรียน (Student)
1. การเข้าสู่ระบบ (Authentication)
   * Action: นักเรียนเปิดโปรแกรม แสดงหน้า LoginView ทำการกรอก Username(student ID)/Password(student ID) จากนั้นกด “Login”
   * Behind the Scenes:
     	1. `LoginView` ส่งข้อมูลไปที่ `AuthController`
     	2. `AuthController` เรียก `AuthService` ตรวจสอบความถูกต้อง
     	3. ถ้าเป็น Admin จริง ทำการปิด LoginView และเปิดหน้า StudentDashboardView
   * Result: นักเรียนเข้าสู่แดชบอร์ดส่วนตัว
2. การดูข้อมูลและผลการเรียน
   * Action: เข้าสู่หน้า StudentDashboardView จะเห็นข้อมูลตัวเอง
   * Behind the Scenes:
     	* Controller ดึงข้อมูลจาก `StudentRepository` และ `RegisteredSubjectRepository`
     	* ส่งข้อมูลไปแสดงใน View
   * Result: นักเรียนเห็นข้อมูลส่วนตัวและตารางวิชา/เกรด
3. การเข้าสู่หน้าลงทะเบียนเรียน (Navigate to Registration)
 	* Action: กดปุ่ม “Register for New Courses”
 	* Behind the Scenes:
  	 	* Controller ดึงข้อมูลวิชาที่นักเรียนยังไม่ได้ลงทะเบียนหรือสอบไม่ผ่าน
   		* เปิดหน้า CourseRegistrationView พร้อมส่งรายการวิชา
 	* Result: นักเรียนเห็นวิชาที่สามารถลงทะเบียนได้
4. การลงทะเบียนเรียน (Course Registration)
	* Action: เลือกวิชาจากนั้นกด “Register”
	* Behind the Scenes:
  		* Controller ตรวจสอบเงื่อนไขวิชาบังคับจาก `RegisteredSubjectRepository`
  		* ถ้าผ่าน จะบันทึกลง CSV
   		* ถ้าไม่ผ่าน จะแสดงข้อความแจ้งเตือน
	* Result:
  		* สำเร็จ: แสดงข้อความ “Registration successful!” จากนั้นตารางวิชาอัปเดต (เกรดว่าง)
  		* ล้มเหลว: แจ้งเตือน และอยู่ที่หน้า `CourseRegistrationView`














