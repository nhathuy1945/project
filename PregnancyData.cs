namespace PregnancyTrackingBackend.Models;

public class PregnancyData
{
    public int Id { get; set; }
    public int UserId { get; set; }
    public int Week { get; set; }
    public double Weight { get; set; }
    public string BloodPressure { get; set; } = string.Empty;
    public string Nutrition { get; set; } = string.Empty;
    public string Milestone { get; set; } = string.Empty;
}
