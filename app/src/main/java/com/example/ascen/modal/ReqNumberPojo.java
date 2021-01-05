package com.example.ascen.modal;

public class ReqNumberPojo {
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
        private String NUMBERSEQUENCE;

        private String EMPCODE;

        public String getNUMBERSEQUENCE ()
        {
            return NUMBERSEQUENCE;
        }

        public void setNUMBERSEQUENCE (String NUMBERSEQUENCE)
        {
            this.NUMBERSEQUENCE = NUMBERSEQUENCE;
        }

        public String getEMPCODE ()
        {
            return EMPCODE;
        }

        public void setEMPCODE (String EMPCODE)
        {
            this.EMPCODE = EMPCODE;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [NUMBERSEQUENCE = "+NUMBERSEQUENCE+", EMPCODE = "+EMPCODE+"]";
        }
    }

}
