class Hello:
    def __init__(self, name):
        self._name = name

    def sayHello(self):
        # print "Hello Python"
        print "hello {0}".format(self._name)


class Hi(Hello):
    def __init__(self, name):
        Hello.__init__(self, name)

    def sayHi(self):
        print "hello {0}".format(self._name)


h = Hello("bbz")
h.sayHello()

hi = Hi("Hi")
hi.sayHi()
