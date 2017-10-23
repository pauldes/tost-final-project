import json;

json_data = open('googleapi.json').read()

obj = json.loads(json_data)

places_names = obj['results']

for i in places_names :
    print(i['name'], " - ", i['types'])


