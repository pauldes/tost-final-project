import mysql.connector

cnx = mysql.connector.connect (
    user='root',
    database='tost_db',
    host='localhost'
)

cursor = cnx.cursor()

query2 = ("SELECT place_tag.place_tag_name AS tag_name, place_cat.place_cat_name AS category_name, id_place_tag_bel_to_cat "
         "FROM place_tag, place_cat, place_tag_belongs_to_cat "
         "WHERE place_tag.id_place_tag = place_tag_belongs_to_cat.id_place_tag AND place_cat.id_place_cat = place_tag_belongs_to_cat.id_place_cat"
         )

cursor.execute(query)

for (tag_name, category_name, id_place_tag_bel_to_cat) in cursor:
    print(tag_name + ' tag belongs to catgeory ' + category_name)

cursor.close()
cnx.close()