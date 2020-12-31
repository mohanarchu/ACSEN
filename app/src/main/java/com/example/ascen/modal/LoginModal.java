package com.example.ascen.modal;

public class LoginModal {
    private Result[] result;

    public Result[] getResult ()
    {
        return result;
    }

    public void setResult (Result[] result)
    {
        this.result = result;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [result = "+result+"]";
    }
    public class Result
    {
        private String Site;

        private String TerritoryName;

        private Warehouse Warehouse;

        private String Acting;

        private String DeviceId;

        private String GPSStatus;

        private String EmpCode;

        private String UserStatus;

        private String EmpName;

        private String POSCode;

        private InventSite InventSite;

        private String Id;

        private String Dcode;

        private String Password;

        public String getSite ()
        {
            return Site;
        }

        public void setSite (String Site)
        {
            this.Site = Site;
        }

        public String getTerritoryName ()
        {
            return TerritoryName;
        }

        public void setTerritoryName (String TerritoryName)
        {
            this.TerritoryName = TerritoryName;
        }

        public Warehouse getWarehouse ()
        {
            return Warehouse;
        }

        public void setWarehouse (Warehouse Warehouse)
        {
            this.Warehouse = Warehouse;
        }

        public String getActing ()
        {
            return Acting;
        }

        public void setActing (String Acting)
        {
            this.Acting = Acting;
        }

        public String getDeviceId ()
        {
            return DeviceId;
        }

        public void setDeviceId (String DeviceId)
        {
            this.DeviceId = DeviceId;
        }

        public String getGPSStatus ()
        {
            return GPSStatus;
        }

        public void setGPSStatus (String GPSStatus)
        {
            this.GPSStatus = GPSStatus;
        }

        public String getEmpCode ()
        {
            return EmpCode;
        }

        public void setEmpCode (String EmpCode)
        {
            this.EmpCode = EmpCode;
        }

        public String getUserStatus ()
        {
            return UserStatus;
        }

        public void setUserStatus (String UserStatus)
        {
            this.UserStatus = UserStatus;
        }

        public String getEmpName ()
        {
            return EmpName;
        }

        public void setEmpName (String EmpName)
        {
            this.EmpName = EmpName;
        }

        public String getPOSCode ()
        {
            return POSCode;
        }

        public void setPOSCode (String POSCode)
        {
            this.POSCode = POSCode;
        }

        public InventSite getInventSite ()
        {
            return InventSite;
        }

        public void setInventSite (InventSite InventSite)
        {
            this.InventSite = InventSite;
        }

        public String getId ()
        {
            return Id;
        }

        public void setId (String Id)
        {
            this.Id = Id;
        }

        public String getDcode ()
        {
            return Dcode;
        }

        public void setDcode (String Dcode)
        {
            this.Dcode = Dcode;
        }

        public String getPassword ()
        {
            return Password;
        }

        public void setPassword (String Password)
        {
            this.Password = Password;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [Site = "+Site+", TerritoryName = "+TerritoryName+", Warehouse = "+Warehouse+", Acting = "+Acting+", DeviceId = "+DeviceId+", GPSStatus = "+GPSStatus+", EmpCode = "+EmpCode+", UserStatus = "+UserStatus+", EmpName = "+EmpName+", POSCode = "+POSCode+", InventSite = "+InventSite+", Id = "+Id+", Dcode = "+Dcode+", Password = "+Password+"]";
        }

        public class InventSite
        {
            private String[] tm;

            public String[] getTm ()
            {
                return tm;
            }

            public void setTm (String[] tm)
            {
                this.tm = tm;
            }

            @Override
            public String toString()
            {
                return "ClassPojo [tm = "+tm+"]";
            }
        }

        public class Warehouse
        {
            private Tm[] tm;

            public Tm[] getTm ()
            {
                return tm;
            }

            public void setTm (Tm[] tm)
            {
                this.tm = tm;
            }

            @Override
            public String toString()
            {
                return "ClassPojo [tm = "+tm+"]";
            }
            public class Tm
            {
                private String wareHouseId;

                private String Name;

                public String getWareHouseId ()
                {
                    return wareHouseId;
                }

                public void setWareHouseId (String wareHouseId)
                {
                    this.wareHouseId = wareHouseId;
                }

                public String getName ()
                {
                    return Name;
                }

                public void setName (String Name)
                {
                    this.Name = Name;
                }

                @Override
                public String toString()
                {
                    return "ClassPojo [wareHouseId = "+wareHouseId+", Name = "+Name+"]";
                }
            }
        }
    }
}
