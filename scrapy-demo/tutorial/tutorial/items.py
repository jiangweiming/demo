# !/usr/bin/env python
# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# http://doc.scrapy.org/en/latest/topics/items.html

import scrapy

class DmozItem(scrapy.Item):
    """docstring for DmozItem"""
    title = scrapy.Field()
    link = scrapy.Field()
    desc = scrapy.Field()
        