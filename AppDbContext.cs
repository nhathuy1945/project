using Microsoft.EntityFrameworkCore;
using PregnancyTrackingBackend.Models;

namespace PregnancyTrackingBackend.Data
{
    public class AppDbContext : DbContext
    {
        public AppDbContext(DbContextOptions<AppDbContext> options) : base(options)
        {
        }

        public DbSet<User> Users { get; set; }
        public DbSet<PregnancyData> PregnancyData { get; set; }
        public DbSet<Notification> Notifications { get; set; }
        public DbSet<Labels> Labels { get; set; }
    }
}