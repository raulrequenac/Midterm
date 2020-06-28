INSERT INTO address(country, city, post_code, street, portal_number, floor, door) VALUES ('Spain', 'Malaga', 29005, 'Larios', 10, 3, 'D');
INSERT INTO user(name, username, password, logged_in) VALUES
    ('admin', 'admin', '$2a$10$lOFAhMkWU0g8//1M9bvZ..etpcn499yZlYezxseBIfzRql8ic2EbS', false),
    ('account-holder', 'account-holder', '$2a$10$5aujRGbuxxgcKucnLFbppuyzogrsec0qYux1l5uu7vxlXOOjOmVbe', false),
    ('third-party', 'third-party', '$2a$10$B.rwQundY6ClY8sqLeCsxurPRP7KhspBF1k1sj8CdI5WlT5jD5fb6', false);
INSERT INTO role(role, user_id) VALUES
    ('ROLE_ADMIN', 1),
    ('ROLE_ACCOUNT_HOLDER', 2),
    ('ROLE_THIRD_PARTY', 3);
INSERT INTO admin(id) VALUES (1);
INSERT INTO account_holder(id, date_of_birth, address_id) VALUES (2, '1997-10-22', 1);
INSERT INTO third_party(id, hashed_key) VALUES (3, '$2a$10$B.rwQundY6ClY8sqLeCsxurPRP7KhspBF1k1sj8CdI5WlT5jD5fb6');
INSERT INTO checking(amount, currency, minimum_balance, monthly_maintenance_fee, penalty_fee, secret_key, status, primary_owner_id) VALUES
    (300, 'USD', 250, 12, 40, '1234', 'ACTIVE', 2),
    (300, 'USD', 250, 12, 40, '1234', 'ACTIVE', 2),
    (300, 'USD', 250, 12, 40, '1234', 'ACTIVE', 2),
    (300, 'USD', 250, 12, 40, '1234', 'ACTIVE', 2);
INSERT INTO student_checking(id) VALUES (2);
INSERT INTO savings(id, interest_rate, interest_date) VALUES (3, 0.5, NOW());
INSERT INTO credit_card(id, credit_limit) VALUES (4, 100000);
