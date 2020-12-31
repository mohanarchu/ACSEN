package com.example.ascen.modal;

public class StateModal {
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
        private String CUSTGROUP;

        private String NAME;

        public String getCUSTGROUP ()
        {
            return CUSTGROUP;
        }

        public void setCUSTGROUP (String CUSTGROUP)
        {
            this.CUSTGROUP = CUSTGROUP;
        }

        public String getNAME ()
        {
            return NAME;
        }

        public void setNAME (String NAME)
        {
            this.NAME = NAME;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [CUSTGROUP = "+CUSTGROUP+", NAME = "+NAME+"]";
        }
    }

}
