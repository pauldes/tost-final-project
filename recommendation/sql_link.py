import MySQLdb

db = MySQLdb.connect(host="localhost",    # your host, usually localhost
                     user="root",         # your username
                     passwd="root",  # your password
                     db="tost_db")        # name of the data base

# you must create a Cursor object. It will let
#  you execute all the queries you need
cur = db.cursor()


cur.execute("SELECT * FROM places")
# print the rows
for row in cur.fetchall():
    print('Place #',row[0],' is ',row[1])

print('----')

cur.execute("SELECT * FROM userx")
# print the rows
for row in cur.fetchall():
    print('User #',row[0],' is ',row[1])

print('----')

cur.execute("SELECT * FROM user_liked_places")
# print the rows
for row in cur.fetchall():
    print('User #',row[1],' liked place #',row[2],' with score ',row[3])

'''
insert_stmt = (
  "INSERT INTO employees (emp_no, first_name, last_name, hire_date) "
  "VALUES (%s, %s, %s, %s)"
)
data = (2, 'Jane', 'Doe', datetime.date(2012, 3, 23))
cursor.execute(insert_stmt, data)
'''

db.close()


