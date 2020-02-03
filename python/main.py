import pandas as pd
import numpy as np

from utils import econ_utils


class result_matrix:
    def __init__(self):
        self.cusip = ""

        self.ave_before_before = 0
        self.ave_after_before = 0
        self.qint_diff_before = 0

        self.ave_before_during = 0
        self.ave_after_during = 0
        self.qint_diff_during = 0

        self.ave_before_after = 0
        self.ave_after_after = 0
        self.qint_diff_after = 0
        
        self.sic_ave_before_before = 0
        self.sic_ave_after_before = 0
        self.sic_qint_diff_before = 0

        self.sic_ave_before_during = 0
        self.sic_ave_after_during = 0
        self.sic_qint_diff_during = 0

        self.sic_ave_before_after = 0
        self.sic_ave_after_after = 0
        self.sic_qint_diff_after = 0


class bounded_value:    
    def __init__(self):
        self.start = 0
        self.mid = 0
        self.end = 0
        self.state = ""
        self.beforeAverageTQFirm = 0
        self.afterAverageTQFirm = 0
        self.beforeAverageTQSIC = 0
        self.afterAvewrageTQSIC = 0
        self.quarterlyIntervalTQDifference = 0
        self.quarterlyIntervalTQDifferenceSIC = 0
        self.sic = ""
        self.cusip = ""
        self.quarterSpan = 0

class prototype_interval_class:
    def __init__(self):
        self.path = "/home/jeff/Development/Projects/econ/econTools/python/data"
        self.firms = []
        self.economy = None
        self.utils = econ_utils(self.path)
        self.cusips = 100
        self.timeBlock = 3
        self.dataPoints = 120
        self.firmTimeseries = list()
        self.resTree = {}
        self.treeToWrite = {}
        self.resultMatrix = result_matrix()




print("HERE WE GO")
pic = prototype_interval_class()