list1 = [20, 6, 15, 53, 47]
list2 = [(6, 'John', '20'), (15, 'Kim', '18'),
         (20, 'David', '21'), (47, 'Amy', '23'), (53, 'Mary', '20')]
print(list1)
print(list2)

list3 = []
# count = len(list1)
# for i in range(0, count):
#     dict = {}
#     id = list1[i]
#     dict['id'] = id
#     j = 0
#     while j < count and list2[j][0] != id:
#         j += 1
#     dict['name'] = list2[j][1]
#     dict['age'] = list2[j][2]
#     list3.append(dict)

# list2_dict = {}
# for item2 in list2:
#     list2_dict[item2[0]] = (item2[1], item2[2])
# for item1 in list1:
#     dict = {}
#     dict['id'] = item1
#     dict['name'] = list2_dict[item1][0]
#     dict['age'] = list2_dict[item1][1]
#     list3.append(dict)

list2_dict = {}
for item2 in list2:
    list2_dict[item2[0]] = {'name': item2[1], 'age': item2[2]}
for item1 in list1:
    dict = list2_dict[item1]
    dict['id'] = item1
    list3.append(dict)

print(list3)
