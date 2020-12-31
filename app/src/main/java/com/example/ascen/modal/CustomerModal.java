package com.example.ascen.modal;

public class CustomerModal {

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
        private String CustGroup;

        private String Dimension;

        private String CreditMax;

        private String Name;

        private String PostingProfile;

        private String TmId;

        private String AccountNum;

        private String State;

        private String SeedLicNo;

        private String RBMId;

        private String Blocked;

        private String DBMId;

        private String DBMNAME;

        private String GSTNumber_IN;

        private String RBMNAME;

        private String MandatoryCreditLimit;

        public String getCustGroup ()
        {
            return CustGroup;
        }

        public void setCustGroup (String CustGroup)
        {
            this.CustGroup = CustGroup;
        }

        public String getDimension ()
        {
            return Dimension;
        }

        public void setDimension (String Dimension)
        {
            this.Dimension = Dimension;
        }

        public String getCreditMax ()
        {
            return CreditMax;
        }

        public String getDBMId() {
            return DBMId;
        }

        public String getDBMNAME() {
            return DBMNAME;
        }

        public String getRBMNAME() {
            return RBMNAME;
        }

        public void setRBMNAME(String RBMNAME) {
            this.RBMNAME = RBMNAME;
        }

        public void setCreditMax (String CreditMax)
        {
            this.CreditMax = CreditMax;
        }

        public String getName ()
        {
            return Name;
        }

        public void setName (String Name)
        {
            this.Name = Name;
        }

        public String getPostingProfile ()
        {
            return PostingProfile;
        }

        public void setPostingProfile (String PostingProfile)
        {
            this.PostingProfile = PostingProfile;
        }

        public String getTmId ()
        {
            return TmId;
        }

        public void setTmId (String TmId)
        {
            this.TmId = TmId;
        }

        public String getAccountNum ()
        {
            return AccountNum;
        }

        public void setAccountNum (String AccountNum)
        {
            this.AccountNum = AccountNum;
        }

        public String getState ()
        {
            return State;
        }

        public void setState (String State)
        {
            this.State = State;
        }

        public String getSeedLicNo ()
        {
            return SeedLicNo;
        }

        public void setSeedLicNo (String SeedLicNo)
        {
            this.SeedLicNo = SeedLicNo;
        }

        public String getRBMId ()
        {
            return RBMId;
        }

        public void setRBMId (String RBMId)
        {
            this.RBMId = RBMId;
        }

        public String getBlocked ()
        {
            return Blocked;
        }

        public void setBlocked (String Blocked)
        {
            this.Blocked = Blocked;
        }

        public String getGSTNumber_IN ()
        {
            return GSTNumber_IN;
        }

        public void setGSTNumber_IN (String GSTNumber_IN)
        {
            this.GSTNumber_IN = GSTNumber_IN;
        }

        public String getMandatoryCreditLimit ()
        {
            return MandatoryCreditLimit;
        }

        public void setMandatoryCreditLimit (String MandatoryCreditLimit)
        {
            this.MandatoryCreditLimit = MandatoryCreditLimit;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [CustGroup = "+CustGroup+", Dimension = "+Dimension+", CreditMax = "+CreditMax+", Name = "+Name+", PostingProfile = "+PostingProfile+", TmId = "+TmId+", AccountNum = "+AccountNum+", State = "+State+", SeedLicNo = "+SeedLicNo+", RBMId = "+RBMId+", Blocked = "+Blocked+", GSTNumber_IN = "+GSTNumber_IN+", MandatoryCreditLimit = "+MandatoryCreditLimit+"]";
        }
    }


}
