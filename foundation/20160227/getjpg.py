# coding=utf-8

from urllib import request


def getHtml(url):
    page = request.urlopen(url)
    html = page.read()
    return html


result = getHtml("http://tieba.baidu.com/p/4376654386")

print(result)
