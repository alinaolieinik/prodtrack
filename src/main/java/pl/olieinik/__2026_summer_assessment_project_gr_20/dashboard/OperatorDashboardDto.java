package pl.olieinik.__2026_summer_assessment_project_gr_20.dashboard;

import pl.olieinik.__2026_summer_assessment_project_gr_20.announcement.Announcement;

import java.util.List;

public class OperatorDashboardDto {

    private Long operatorId;

    private String name;

    private String surname;

    private String machineName;

    private String productName;

    private Integer packedCount;

    private Integer targetPerShift;

    private Integer exceedTargetPerShift;

    private Integer minimumPerShift;

    private Double secondsPerPackage;

    private Long remainingSeconds;

    private List<Announcement> announcements;

    public OperatorDashboardDto() {
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getPackedCount() {
        return packedCount;
    }

    public void setPackedCount(Integer packedCount) {
        this.packedCount = packedCount;
    }

    public Integer getTargetPerShift() {
        return targetPerShift;
    }

    public void setTargetPerShift(Integer targetPerShift) {
        this.targetPerShift = targetPerShift;
    }

    public Integer getExceedTargetPerShift() {
        return exceedTargetPerShift;
    }

    public void setExceedTargetPerShift(Integer exceedTargetPerShift) {
        this.exceedTargetPerShift = exceedTargetPerShift;
    }

    public Integer getMinimumPerShift() {
        return minimumPerShift;
    }

    public void setMinimumPerShift(Integer minimumPerShift) {
        this.minimumPerShift = minimumPerShift;
    }

    public Double getSecondsPerPackage() {
        return secondsPerPackage;
    }

    public void setSecondsPerPackage(Double secondsPerPackage) {
        this.secondsPerPackage = secondsPerPackage;
    }

    public Long getRemainingSeconds() {
        return remainingSeconds;
    }

    public void setRemainingSeconds(Long remainingSeconds) {
        this.remainingSeconds = remainingSeconds;
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
    }
}
