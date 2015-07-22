#!/usr/bin/env python
# -*- coding: utf-8 -*-

import scrapy
from scrapy.spiders import CrawlSpider, Rule
from scrapy.linkextractors import LinkExtractor

class MyCrawlSpider(CrawlSpider):
    """docstring for MyCrawlSpider"""
    name = 'crawlspider'
    allowed_domains = ['example.com']
    start_urls = ['http://www.example.com']
        
    rules = (
        Rule(LinkExtractor(allow=('category\.php',), deny=('subsection\.php',))),
        Rule(LinkExtractor(allow=('item\.php',)), callback='parse_item')
    )

    def parse_item(self, response):
        self.logger.info('Hi, this is an item page! %s', response_url)
        item = scrapy.Item()
        item['id'] = response.xpath('//td[@id="item_id"]/text()').re(r'ID: (\d+)')
        item['name'] = response.xpath('//td[@id="item_name"]/text()').extract()
        item['desc'] = response.xpath('//td[@id="item_desc"]/text()').extract()
        yield item
