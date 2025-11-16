package builder;

public class PcConfigurator {


    public class PC{
        public String CPU;
        public String GPU;
        public int Ram;
        public int Storage;
    }


    interface PCBuilder{
        PCBuilder setCPU(String CPU);
        PCBuilder setGPU(String GPU);
        PCBuilder setRam(int Ram);
        PCBuilder setStorage(int Storage);
    }



    public class PcBuilderConf implements PCBuilder{
        PC pc = new PC();
        @Override
        public PCBuilder setCPU(String CPU) {
            pc.CPU = CPU;
            return this;
        }

        @Override
        public PCBuilder setGPU(String GPU) {
            pc.GPU = GPU;
            return this;
        }

        @Override
        public PCBuilder setRam(int Ram) {
            pc.Ram = Ram;
            return this;
        }

        @Override
        public PCBuilder setStorage(int Storage) {
            pc.Storage = Storage;
            return this;
        }
    }




}
