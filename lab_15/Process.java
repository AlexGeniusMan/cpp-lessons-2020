public class Process implements Comparable<Process>{
    private int timeOfBegin;
    private int timeOfEnd;

    public Process(int timeOfBegin, int timeOfEnd) {
        this.timeOfBegin = timeOfBegin;
        this.timeOfEnd = timeOfEnd;
    }

    public int getTimeOfBegin() {
        return timeOfBegin;
    }

    public void setTimeOfBegin(int timeOfBegin) {
        this.timeOfBegin = timeOfBegin;
    }

    public int getTimeOfEnd() {
        return timeOfEnd;
    }

    public void setTimeOfEnd(int timeOfEnd) {
        this.timeOfEnd = timeOfEnd;
    }

    @Override
    public int compareTo(Process process) {
        if(this.timeOfEnd < process.timeOfEnd) return -1;
        else if(this.timeOfEnd > process.timeOfEnd) return 1;
        else return 0;
    }

    @Override
    public String toString() {
        return "Process{" +
                "start=" + timeOfBegin +
                ", finish=" + timeOfEnd +
                '}';
    }
}s