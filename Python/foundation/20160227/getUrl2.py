# coding=utf-8
import urllib.parse
import urllib.request

data = {}
data['word'] = '八宝粥'

url = "http://www.baidu.com/s?"
url_values = urllib.parse.urlencode(data)
url_full = url + url_values

data = urllib.request.urlopen(url_full).read()
data = data.decode('utf-8')

print(data)
