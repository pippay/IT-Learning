std1 = {"name": "aaa", "age": 20}
std2 = {"name": "bbb", "age": 30}


def print_std(std):
    print("%s:%s" % (std["name"], std["age"]))


print_std(std1)
print_std(std2)


class Student(object):
    def __init__(self, name, age):
        self.__name = name
        self.__age = age

    def print_student(self):
        print("ybbz : %s : %s" % (self.__name, self.__age))

    def set_name(self, name):
        if name == "gogogo":
            raise ValueError('gogogo error')
        else:
            self.__name = name

    def get_name(self):
        return self.__name


stu1 = Student("ccc", 22)
stu1.print_student()

# stu1.set_name("gogogo")
print(stu1.get_name())
