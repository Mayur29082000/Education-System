-- Insert 5 Colleges
INSERT INTO college (college_id, name, address) VALUES (101, 'Engineering College A', 'Pune, Maharashtra');
INSERT INTO college (college_id, name, address) VALUES (102, 'Arts & Science College B', 'Mumbai, Maharashtra');
INSERT INTO college (college_id, name, address) VALUES (103, 'Medical College C', 'Delhi, India');
INSERT INTO college (college_id, name, address) VALUES (104, 'Management Institute D', 'Bengaluru, Karnataka');
INSERT INTO college (college_id, name, address) VALUES (105, 'Law University E', 'Chennai, Tamil Nadu');

-- Insert 5 Departments (associated with College A, B, C)
INSERT INTO department (department_id, name, code, college_college_id) VALUES (201, 'Computer Science', 'CS', 101);
INSERT INTO department (department_id, name, code, college_college_id) VALUES (202, 'Electronics', 'ENTC', 101);
INSERT INTO department (department_id, name, code, college_college_id) VALUES (203, 'Physics', 'PHY', 102);
INSERT INTO department (department_id, name, code, college_college_id) VALUES (204, 'Chemistry', 'CHEM', 102);
INSERT INTO department (department_id, name, code, college_college_id) VALUES (205, 'Mechanical Engineering', 'MECH', 103);

-- Insert 5 Teachers (associated with different departments)
INSERT INTO teacher (teacher_id, name, degree, department_department_id) VALUES (301, 'Dr. Alice Smith', 'Ph.D. CS', 201);
INSERT INTO teacher (teacher_id, name, degree, department_department_id) VALUES (302, 'Prof. Bob Johnson', 'M.Tech ENTC', 202);
INSERT INTO teacher (teacher_id, name, degree, department_department_id) VALUES (303, 'Dr. Carol White', 'Ph.D. Physics', 203);
INSERT INTO teacher (teacher_id, name, degree, department_department_id) VALUES (304, 'Prof. David Brown', 'M.Sc. Chemistry', 204);
INSERT INTO teacher (teacher_id, name, degree, department_department_id) VALUES (305, 'Dr. Emily Green', 'Ph.D. MECH', 205);

-- Insert 5 Students (associated with different departments)
INSERT INTO student (student_id, name, email, department_department_id) VALUES (401, 'Student A', 'student.a@example.com', 201);
INSERT INTO student (student_id, name, email, department_department_id) VALUES (402, 'Student B', 'student.b@example.com', 202);
INSERT INTO student (student_id, name, email, department_department_id) VALUES (403, 'Student C', 'student.c@example.com', 203);
INSERT INTO student (student_id, name, email, department_department_id) VALUES (404, 'Student D', 'student.d@example.com', 204);
INSERT INTO student (student_id, name, email, department_department_id) VALUES (405, 'Student E', 'student.e@example.com', 201);