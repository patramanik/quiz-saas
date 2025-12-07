CREATE TABLE organizations (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    status VARCHAR(50) NOT NULL,
    subscription_plan VARCHAR(50),
    subscription_start TIMESTAMP,
    subscription_end TIMESTAMP,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    organization_id BIGINT REFERENCES organizations(id),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE api_keys (
    id BIGSERIAL PRIMARY KEY,
    key VARCHAR(255) NOT NULL UNIQUE,
    status VARCHAR(50) NOT NULL,
    organization_id BIGINT NOT NULL REFERENCES organizations(id),
    created_at TIMESTAMP
);

CREATE TABLE classrooms (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    section VARCHAR(255),
    organization_id BIGINT NOT NULL REFERENCES organizations(id),
    teacher_id BIGINT REFERENCES users(id)
);

CREATE TABLE classroom_students (
    classroom_id BIGINT NOT NULL REFERENCES classrooms(id),
    student_id BIGINT NOT NULL REFERENCES users(id)
);

CREATE TABLE subjects (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    organization_id BIGINT NOT NULL REFERENCES organizations(id)
);

CREATE TABLE questions (
    id BIGSERIAL PRIMARY KEY,
    content TEXT NOT NULL,
    option_a VARCHAR(255) NOT NULL,
    option_b VARCHAR(255) NOT NULL,
    option_c VARCHAR(255),
    option_d VARCHAR(255),
    correct_option VARCHAR(10) NOT NULL,
    subject_id BIGINT NOT NULL REFERENCES subjects(id),
    creator_id BIGINT NOT NULL REFERENCES users(id)
);

CREATE TABLE quizzes (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    subject_id BIGINT NOT NULL REFERENCES subjects(id),
    classroom_id BIGINT NOT NULL REFERENCES classrooms(id),
    teacher_id BIGINT NOT NULL REFERENCES users(id),
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    duration_minutes INTEGER
);

CREATE TABLE quiz_questions (
    quiz_id BIGINT NOT NULL REFERENCES quizzes(id),
    question_id BIGINT NOT NULL REFERENCES questions(id)
);

CREATE TABLE quiz_attempts (
    id BIGSERIAL PRIMARY KEY,
    quiz_id BIGINT NOT NULL REFERENCES quizzes(id),
    student_id BIGINT NOT NULL REFERENCES users(id),
    score INTEGER,
    total_questions INTEGER,
    attempt_time TIMESTAMP
);

CREATE TABLE payment_transactions (
    id BIGSERIAL PRIMARY KEY,
    organization_id BIGINT NOT NULL REFERENCES organizations(id),
    transaction_id VARCHAR(255) NOT NULL,
    amount DECIMAL(19, 2) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    status VARCHAR(50) NOT NULL,
    gateway VARCHAR(50) NOT NULL,
    transaction_date TIMESTAMP
);
