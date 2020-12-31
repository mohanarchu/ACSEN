package com.example.ascen.presenter;

public class CustomerInvModal {

    private String Status;

    private Response[] Response;

    public String getStatus ()
    {
        return Status;
    }

    public void setStatus (String Status)
    {
        this.Status = Status;
    }

    public Response[] getResponse ()
    {
        return Response;
    }

    public void setResponse (Response[] Response)
    {
        this.Response = Response;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Status = "+Status+", Response = "+Response+"]";
    }
    public class Response
    {
        private String SALESQTY;

        private String ACCOUNTNUM;

        private String INVOICEID;

        private String INVOICEDATE;

        private String LINENUM;

        private String SALESID;

        private String ITEMID;

        private String NAME;

        private String ITEMNAME;

        private String TRANSFERQTY;


        public String getTRANSFERQTY() {
            return TRANSFERQTY;
        }

        public void setTRANSFERQTY(String TRANSFERQTY) {
            this.TRANSFERQTY = TRANSFERQTY;
        }

        public String getSALESQTY ()
        {
            return SALESQTY;
        }

        public void setSALESQTY (String SALESQTY)
        {
            this.SALESQTY = SALESQTY;
        }

        public String getACCOUNTNUM ()
        {
            return ACCOUNTNUM;
        }

        public void setACCOUNTNUM (String ACCOUNTNUM)
        {
            this.ACCOUNTNUM = ACCOUNTNUM;
        }

        public String getINVOICEID ()
        {
            return INVOICEID;
        }

        public void setINVOICEID (String INVOICEID)
        {
            this.INVOICEID = INVOICEID;
        }

        public String getINVOICEDATE ()
        {
            return INVOICEDATE;
        }

        public void setINVOICEDATE (String INVOICEDATE)
        {
            this.INVOICEDATE = INVOICEDATE;
        }

        public String getLINENUM ()
        {
            return LINENUM;
        }

        public void setLINENUM (String LINENUM)
        {
            this.LINENUM = LINENUM;
        }

        public String getSALESID ()
        {
            return SALESID;
        }

        public void setSALESID (String SALESID)
        {
            this.SALESID = SALESID;
        }

        public String getITEMID ()
        {
            return ITEMID;
        }

        public void setITEMID (String ITEMID)
        {
            this.ITEMID = ITEMID;
        }

        public String getNAME ()
        {
            return NAME;
        }

        public void setNAME (String NAME)
        {
            this.NAME = NAME;
        }

        public String getITEMNAME ()
        {
            return ITEMNAME;
        }

        public void setITEMNAME (String ITEMNAME)
        {
            this.ITEMNAME = ITEMNAME;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [SALESQTY = "+SALESQTY+", ACCOUNTNUM = "+ACCOUNTNUM+", INVOICEID = "+INVOICEID+", INVOICEDATE = "+INVOICEDATE+", LINENUM = "+LINENUM+", SALESID = "+SALESID+", ITEMID = "+ITEMID+", NAME = "+NAME+", ITEMNAME = "+ITEMNAME+"]";
        }
    }


}
