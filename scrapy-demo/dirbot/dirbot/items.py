# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# http://doc.scrapy.org/en/latest/topics/items.html

import scrapy

class Website(scrapy.Item):
    """docstring for Website"""
    name = scrapy.Field()
    description = scrapy.Field()
    url = scrapy.Field()
