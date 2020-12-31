package com.example.ascen.modal;

public class ToCustomerModal {
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
        private String ACCOUNTNUM;

        private String DBMNAME;

        private String CUSTGROUP;

        private String ZONEID;

        private String TERRITORY_EMPNAME;

        private String DBMId;

        private String RBMNAME;

        private String TERRITORY_EMP;

        private String RBMID;

        private String NAME;

        private String REGIONID;

        public String getACCOUNTNUM ()
        {
            return ACCOUNTNUM;
        }

        public void setACCOUNTNUM (String ACCOUNTNUM)
        {
            this.ACCOUNTNUM = ACCOUNTNUM;
        }

        public String getDBMNAME ()
        {
            return DBMNAME;
        }

        public void setDBMNAME (String DBMNAME)
        {
            this.DBMNAME = DBMNAME;
        }

        public String getCUSTGROUP ()
        {
            return CUSTGROUP;
        }

        public void setCUSTGROUP (String CUSTGROUP)
        {
            this.CUSTGROUP = CUSTGROUP;
        }

        public String getZONEID ()
        {
            return ZONEID;
        }

        public void setZONEID (String ZONEID)
        {
            this.ZONEID = ZONEID;
        }

        public String getTERRITORY_EMPNAME ()
        {
            return TERRITORY_EMPNAME;
        }

        public void setTERRITORY_EMPNAME (String TERRITORY_EMPNAME)
        {
            this.TERRITORY_EMPNAME = TERRITORY_EMPNAME;
        }

        public String getDBMId ()
        {
            return DBMId;
        }

        public void setDBMId (String DBMId)
        {
            this.DBMId = DBMId;
        }

        public String getRBMNAME ()
        {
            return RBMNAME;
        }

        public void setRBMNAME (String RBMNAME)
        {
            this.RBMNAME = RBMNAME;
        }

        public String getTERRITORY_EMP ()
        {
            return TERRITORY_EMP;
        }

        public void setTERRITORY_EMP (String TERRITORY_EMP)
        {
            this.TERRITORY_EMP = TERRITORY_EMP;
        }

        public String getRBMID ()
        {
            return RBMID;
        }

        public void setRBMID (String RBMID)
        {
            this.RBMID = RBMID;
        }

        public String getNAME ()
        {
            return NAME;
        }

        public void setNAME (String NAME)
        {
            this.NAME = NAME;
        }

        public String getREGIONID ()
        {
            return REGIONID;
        }

        public void setREGIONID (String REGIONID)
        {
            this.REGIONID = REGIONID;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [ACCOUNTNUM = "+ACCOUNTNUM+", DBMNAME = "+DBMNAME+", CUSTGROUP = "+CUSTGROUP+", ZONEID = "+ZONEID+", TERRITORY_EMPNAME = "+TERRITORY_EMPNAME+", DBMId = "+DBMId+", RBMNAME = "+RBMNAME+", TERRITORY_EMP = "+TERRITORY_EMP+", RBMID = "+RBMID+", NAME = "+NAME+", REGIONID = "+REGIONID+"]";
        }
    }


}
