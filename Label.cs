using System.ComponentModel.DataAnnotations;

namespace PregnancyTrackingBackend.Models
{
    public class Labels
    {
        public int Id { get; set; }

        [Required]
        public string Name { get; set; }

        public string Description { get; set; }
    }
}# project
