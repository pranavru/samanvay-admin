# Samanvay Admin Application Deployment Guide

This guide will walk you through deploying the Samanvay Admin Application on a remote server.

## Prerequisites

1. A Linux server (Ubuntu 20.04 LTS recommended)
2. Domain name (optional)
3. Node.js 20.x or later
4. MongoDB 6.0 or later
5. PM2 (Process Manager)
6. Nginx (for reverse proxy)

## Step 1: Server Setup

```bash
# Update system packages
sudo apt update
sudo apt upgrade -y

# Install Node.js 20.x
curl -fsSL https://deb.nodesource.com/setup_20.x | sudo -E bash -
sudo apt install -y nodejs

# Verify installation
node --version
npm --version

# Install PM2 globally
sudo npm install -g pm2
```

## Step 2: Install MongoDB

```bash
# Import MongoDB public key
curl -fsSL https://pgp.mongodb.com/server-6.0.asc | \
   sudo gpg -o /usr/share/keyrings/mongodb-server-6.0.gpg \
   --dearmor

# Add MongoDB repository
echo "deb [ arch=amd64,arm64 signed-by=/usr/share/keyrings/mongodb-server-6.0.gpg ] https://repo.mongodb.org/apt/ubuntu focal/mongodb-org/6.0 multiverse" | \
    sudo tee /etc/apt/sources.list.d/mongodb-org-6.0.list

# Install MongoDB
sudo apt update
sudo apt install -y mongodb-org

# Start MongoDB and enable on boot
sudo systemctl start mongod
sudo systemctl enable mongod

# Verify MongoDB is running
sudo systemctl status mongod
```

## Step 3: Application Deployment

```bash
# Create application directory
sudo mkdir -p /var/www/samanvay-admin
sudo chown -R $USER:$USER /var/www/samanvay-admin

# Clone the repository
git clone https://github.com/your-repo/samanvay-admin.git /var/www/samanvay-admin

# Install dependencies
cd /var/www/samanvay-admin
npm install --production
```

## Step 4: Environment Configuration

Create a production environment file:

```bash
# Create .env file
nano /var/www/samanvay-admin/.env
```

Add the following configuration:

```env
PORT=3001
MONGODB_URI=mongodb://localhost:27017/samanvay_admin
JWT_SECRET=your_secure_production_secret_key
JWT_EXPIRES_IN=7d
NODE_ENV=production
```

## Step 5: Setup PM2 Process Manager

```bash
# Start the application with PM2
cd /var/www/samanvay-admin
pm2 start src/index.js --name "samanvay-admin"

# Make PM2 auto-start on system boot
pm2 startup
pm2 save
```

## Step 6: Install and Configure Nginx

```bash
# Install Nginx
sudo apt install -y nginx

# Remove default configuration
sudo rm /etc/nginx/sites-enabled/default

# Create new Nginx configuration
sudo nano /etc/nginx/sites-available/samanvay-admin
```

Add the following Nginx configuration:

```nginx
server {
    listen 80;
    server_name your-domain.com;  # Replace with your domain

    location / {
        proxy_pass http://localhost:3001;
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection 'upgrade';
        proxy_set_header Host $host;
        proxy_cache_bypass $http_upgrade;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }
}
```

Enable the configuration:

```bash
# Create symbolic link
sudo ln -s /etc/nginx/sites-available/samanvay-admin /etc/nginx/sites-enabled/

# Test Nginx configuration
sudo nginx -t

# Restart Nginx
sudo systemctl restart nginx
```

## Step 7: Setup SSL with Let's Encrypt (Optional)

```bash
# Install Certbot
sudo apt install -y certbot python3-certbot-nginx

# Obtain SSL certificate
sudo certbot --nginx -d your-domain.com

# Verify auto-renewal is enabled
sudo systemctl status certbot.timer
```

## Step 8: MongoDB Security (Production)

```bash
# Create MongoDB admin user
mongosh admin --eval '
  db.createUser({
    user: "admin",
    pwd: "your_secure_password",
    roles: [ { role: "userAdminAnyDatabase", db: "admin" } ]
  })
'

# Enable MongoDB authentication
sudo nano /etc/mongod.conf
```

Add/modify these lines in mongod.conf:

```yaml
security:
  authorization: enabled
```

```bash
# Restart MongoDB
sudo systemctl restart mongod
```

## Step 9: Firewall Configuration

```bash
# Install UFW if not present
sudo apt install -y ufw

# Configure firewall
sudo ufw allow ssh
sudo ufw allow http
sudo ufw allow https
sudo ufw enable
```

## Monitoring and Maintenance

### View Application Logs

```bash
# View PM2 logs
pm2 logs samanvay-admin

# View Nginx access logs
sudo tail -f /var/log/nginx/access.log

# View Nginx error logs
sudo tail -f /var/log/nginx/error.log
```

### Backup MongoDB Database

```bash
# Create backup directory
mkdir -p /var/backups/mongodb

# Backup MongoDB
mongodump --db samanvay_admin --out /var/backups/mongodb/$(date +"%Y%m%d")
```

### Update Application

```bash
# Pull latest changes
cd /var/www/samanvay-admin
git pull origin main

# Install dependencies
npm install --production

# Restart application
pm2 restart samanvay-admin
```

## Troubleshooting

1. If the application isn't accessible:
   - Check if the application is running: `pm2 status`
   - Verify Nginx is running: `sudo systemctl status nginx`
   - Check firewall status: `sudo ufw status`

2. If MongoDB connection fails:
   - Verify MongoDB is running: `sudo systemctl status mongod`
   - Check MongoDB logs: `sudo tail -f /var/log/mongodb/mongod.log`

3. If SSL certificate issues occur:
   - Check certificate status: `sudo certbot certificates`
   - Renew certificates manually: `sudo certbot renew --dry-run`

## Security Best Practices

1. Regularly update system packages:
   ```bash
   sudo apt update && sudo apt upgrade -y
   ```

2. Monitor system resources:
   ```bash
   htop
   df -h
   ```

3. Setup automatic security updates:
   ```bash
   sudo apt install unattended-upgrades
   sudo dpkg-reconfigure -plow unattended-upgrades
   ```

4. Regularly rotate logs:
   ```bash
   sudo logrotate -f /etc/logrotate.conf
   ```

## Support

For additional support or questions, please contact the development team or refer to the project documentation.
