#!/usr/bin/env python
# -*- coding: utf-8 -*-

import scrapy
from scrapy.spiders import XMLFeedSpider

class MyXMLFeedSpider(XMLFeedSpider):
    """docstring for MyXMLFeedSpider"""
    name = 'xmlspider'
    allowed_domains = ['example.com']
    start_urls = ['http://www.example.com/feed.xml']
    iterator = 'iternode' # Actually it's a default value
    itertag = 'item'

    def parse_node(self, response, node):
        self.logger.info('Hi, this is a <%s> node!: %s', self.itertag, ''.join(node.extract()))

        item = scrapy.Itme()
        item['id'] = node.xpath('@id').extract()
        item['name'] = node.xpath('name').extract()
        item['desc'] = node.xpath('desc').extract()
        yield item