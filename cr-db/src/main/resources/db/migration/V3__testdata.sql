
INSERT INTO users (id, login, email, password, enabled) VALUES
('1', 'test', 'jdoe@clearedresumes.com', '$2a$10$BVkDFSdtJbj0oURvZWDMBur.NVvoafMuycwiE2RbIoIn9pv4.lh32', TRUE);

INSERT INTO companies (id, name, plan_type, slots, active) VALUES
('1', 'Basic Company No Slots', 'BASIC', 0, true),
('2', 'Basic Company Slots', 'BASIC', 10, true),
('3', 'Premium Company No Slots', 'PREMIUM', 0, true),
('4', 'Premium Company Slots', 'PREMIUM', 10, true),
('5', 'Enterprise Company No Slots', 'ENTERPRISE', 0, true),
('6', 'Enterprise Company Slots', 'ENTERPRISE', 10, true);

INSERT INTO authorities (user_id, authority) VALUES
('1', 'ADMIN'),
('1', 'USER');

INSERT INTO resumes (id, user_id, status, created, expiration) VALUES
('1', '1', 'UNPUBLISHED', '2016-01-01 02:03:04.000', null);

INSERT INTO resume_introductions (resume_id, full_name, objective) VALUES
('1', 'John Doe', 'Lorem ipsum **dolor** sit amet, consectetur adipiscing elit. Nulla vehicula nulla lacus, et molestie nunc dictum quis. Praesent tellus turpis, suscipit et mauris ac, pulvinar auctor tellus. Donec lectus neque, rhoncus nec posuere sollicitudin, pharetra vel quam. Duis nec nunc nisl. Fusce ultrices ut orci a mattis. Cras eleifend magna risus, id scelerisque enim tempor id. Quisque venenatis congue magna, quis fringilla orci ultricies eget. Sed pharetra libero in sem facilisis, a semper nisi egestas.');

INSERT INTO resume_lcats (id, resume_id, lcat, experience) VALUES
('1', '1', 'Software Engineering', 15);

INSERT INTO contact_infos (id, resume_id, value) VALUES
('1', '1', '555-234-5678');

INSERT INTO work_locations (id, resume_id, state, region) VALUES
('1', '1', 'Maryland', 'Fort Meade');

INSERT INTO key_words (resume_id, word) VALUES
('1', 'java'),
('1', 'python'),
('1', 'cloud'),
('1', 'cyber');

INSERT INTO clearances (id, resume_id, type, organization, polygraph) VALUES
('1', '1', 'TS/SCI', 'National Security Agency', 'Full-Scope');

INSERT INTO educations (id, resume_id, institution, field, degree, year) VALUES
('1', '1', 'University of Maryland', 'Computer Science', 'B.S.', 2005);

INSERT INTO certifications (id, resume_id, certificate, year) VALUES
('1', '1', 'Microsoft Certified Professional', 2006);

INSERT INTO work_summaries (id, resume_id, job_title, employer, begin_date, end_date, summary) VALUES
('1', '1', 'Lead Software Engineer', 'Prior Employer, Inc.', '2014-01-01', '2015-01-01', 'Vivamus porta sapien non fringilla aliquet. Cras porttitor, nunc ac sollicitudin posuere, mauris magna suscipit felis, nec viverra odio diam eu ante. Phasellus dapibus venenatis est vitae fermentum. Duis rutrum tempor augue ac ultricies. Suspendisse rhoncus aliquam metus ac varius. Pellentesque in nisi sed felis mattis sollicitudin ut sed eros. Mauris lacinia mauris non justo dignissim, quis molestie magna commodo. Mauris ultrices nisl a ullamcorper tempor. Mauris molestie sollicitudin felis luctus hendrerit. Etiam et lectus massa. Duis consectetur felis ut ante molestie iaculis. Suspendisse lacinia arcu arcu, sit amet finibus ipsum volutpat vel. Proin ut eleifend odio.\r\n\r\nSed venenatis ligula viverra urna maximus ultricies. Pellentesque elementum enim ut vulputate condimentum. Etiam cursus odio non felis gravida sodales. Donec neque lorem, laoreet eu convallis quis, varius eget leo. Nam varius luctus lorem a auctor. Nam vulputate vitae turpis non ultricies. Vivamus sit amet dui vel magna lobortis placerat quis non mauris. Cras at orci a mi bibendum consequat. Integer nec ultrices sapien, sed ullamcorper lectus. Maecenas sed justo eu magna elementum laoreet. Maecenas ut ipsum augue. Aenean interdum est vel nulla placerat ultrices. Donec nec nibh et ipsum sollicitudin pellentesque. Donec finibus urna in nisl maximus sollicitudin. Nullam gravida, turpis a auctor porta, nulla sapien aliquam sapien, at imperdiet nibh sem congue metus. Nunc eget congue ligula, vitae fermentum quam.\r\n\r\nQuisque ullamcorper turpis sapien, in euismod diam bibendum ac. Quisque eget porta nunc, a molestie felis. Vestibulum at mollis est. Vivamus vestibulum erat eu volutpat hendrerit. Nam quis porta odio, ut vulputate elit. Curabitur ut dui posuere, euismod nulla a, scelerisque leo. Fusce convallis ultricies rutrum. Nam massa massa, euismod vitae massa ac, mollis eleifend nunc. Vestibulum fermentum lectus id nunc lobortis, quis mattis libero condimentum. Nullam non dictum mauris, vitae fringilla massa. Proin at tellus ut orci elementum ultrices sit amet et sapien. Cras lorem massa, aliquet ac turpis ac, mattis viverra lectus. Nam lacinia non magna at lacinia. Fusce condimentum dui lectus, vel mattis est mattis et.'),
('2', '1', 'Principal Software Engineer', 'Some Company, LLC', '2015-01-01', '2016-01-01', 'Sed venenatis ligula viverra urna maximus ultricies. Pellentesque elementum enim ut vulputate condimentum. Etiam cursus odio non felis gravida sodales. Donec neque lorem, laoreet eu convallis quis, varius eget leo. Nam varius luctus lorem a auctor. Nam vulputate vitae turpis non ultricies. Vivamus sit amet dui vel magna lobortis placerat quis non mauris. Cras at orci a mi bibendum consequat. Integer nec ultrices sapien, sed ullamcorper lectus. Maecenas sed justo eu magna elementum laoreet. Maecenas ut ipsum augue. Aenean interdum est vel nulla placerat ultrices. Donec nec nibh et ipsum sollicitudin pellentesque. Donec finibus urna in nisl maximus sollicitudin. Nullam gravida, turpis a auctor porta, nulla sapien aliquam sapien, at imperdiet nibh sem congue metus. Nunc eget congue ligula, vitae fermentum quam.'),
('3', '1', 'Software Subject Matter Expert', '', '2016-01-01', NULL, 'Quisque ullamcorper turpis sapien, in euismod diam bibendum ac. Quisque eget porta nunc, a molestie felis. Vestibulum at mollis est. Vivamus vestibulum erat eu volutpat hendrerit. Nam quis porta odio, ut vulputate elit. Curabitur ut dui posuere, euismod nulla a, scelerisque leo. Fusce convallis ultricies rutrum. Nam massa massa, euismod vitae massa ac, mollis eleifend nunc. Vestibulum fermentum lectus id nunc lobortis, quis mattis libero condimentum. Nullam non dictum mauris, vitae fringilla massa. Proin at tellus ut orci elementum ultrices sit amet et sapien. Cras lorem massa, aliquet ac turpis ac, mattis viverra lectus. Nam lacinia non magna at lacinia. Fusce condimentum dui lectus, vel mattis est mattis et.');

