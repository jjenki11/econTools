from utils import econ_utils

from mappings import mappings

class economy:
    def __init__(self, path):
        self.file_path = path
        self.all_firms = list()
        self.all_firms2 = {}
        self.utilities = econ_utils(self.file_path)

        self.bankrupt_tree = {}
        self.going_concern_tree = {}
        self.bank_tree = {}
        self.firm_tree = {}
        self.before_tree = {}
        self.during_tree = {}
        self.after_tree = {}
        self.sic_tree = {}
        self.category_tree = {}
        self.quarter_tree = {}
        self.cusip_list = list()

        # Describe data in terms of bk, tgt, acq, gc
        self.going_concern_count = 0
        self.target_count = 0
        self.acquirer_count = 0
        self.bankrupt_count = 0
        self.mapping = mappings()
        self.dM = {}
        self.qM = {}