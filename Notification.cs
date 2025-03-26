using System.ComponentModel.DataAnnotations;

namespace PregnancyTrackingBackend.Models
{
    public class Notification
    {
        public int Id { get; set; }

        [Required]
        public int UserId { get; set; }

        [Required]
        public string Message { get; set; }

        [Required]
        public DateTime Date { get; set; }

        public bool Status { get; set; } // true = Read, false = Unread
    }
}