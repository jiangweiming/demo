#!/usr/bin/env python
# -*- coding: utf-8 -*-

import scrapy
from scrapy.spiders import CSVFeedSpider

class MyCSVFeedSpider(CSVFeedSpider):
    """docstring for MyCSVFeedSpider"""
    name = "csvspider"
    allowed_domains = ['example.com']
    start_urls = ['http://www.example.com/feed.csv']
    delimiter = ';'
    quotechar = '\''
    headers = ['id', 'name', 'desc']

    def parse_row(self, response, row):
        self.logger.info('Hi, this is a row!: %r', row)

        item = scrapy.Item()
        item['id'] = row['id']
        item['name'] = row['name']
        item['desc'] = row['desc']
        yield item