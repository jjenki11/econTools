
class firm:
    def __init__(self, cusip="", dateIndex=-1, labor="", sic=""):
        self.date_index = dateIndex
        self.bankrupcy = list()
        self.entries = list()
        self.GC = False
        self.BK = False
        self.labor = labor
        self.category = "" 
        self.gvkey = "" 
        self.datadate = "" 
        self.fyearq = "" 
        self.fqtr = "" 
        self.cusip = cusip
        self.cshpry = ""
        self.txditcq = ""
        self.sic = sic
        self.deflator = "" 
        self.atq = "" 
        self.dlcq = "" 
        self.dlttq = "" 
        self.dpactq = "" 
        self.dpq = "" 
        self.oibdpq = "" 
        self.ppegtq = "" 
        self.prccq = "" 
        self.pstkq = "" 
        self.saleq = "" 
        self.Profitability = "" 
        self.Market_value_equity = "" 
        self.Equity_book_value = "" 
        self.Tobins_Q = "" 
        self.ATO = "" 
        self.NW = "" 

    def set_bankrupcy(self, b):
        self.bankrupcy.append(b)

    def get_bankrupcy(self):
        return self.bankrupcy

    def set_category(self, c):
        self.category = c
        return True
    
    def get_category(self):
        return self.category