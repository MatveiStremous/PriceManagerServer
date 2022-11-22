INSERT INTO company (name, balance, user_id) VALUES('Lalala223232', 10, 2);

SELECT company_id, company.user_id, user.login FROM company
INNER JOIN user on company.user_id = user.user_id
where user.user_id = 1;

    SELECT * FROM company WHERE user_id = 3

