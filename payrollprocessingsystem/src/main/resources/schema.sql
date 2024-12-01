
CREATE TABLE employee (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    empid VARCHAR(255) UNIQUE NOT NULL,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    designation VARCHAR(255)
);

CREATE TABLE event (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employee_id VARCHAR(255) NOT NULL,
    event_type VARCHAR(255) NOT NULL,
    value DOUBLE,
    event_date DATE,
    notes VARCHAR(255),
    FOREIGN KEY (employee_id) REFERENCES employee(emp_id)
);
