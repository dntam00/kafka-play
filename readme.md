## Kafka play

### Connection failure simulation

In file `/etc/pf.custom` and `/etc/pf.conf` add the following lines to block the connection to Kafka and run `sudo pfctl -f /etc/pf.conf`:
```bash
anchor "custom_rules"
load anchor "custom_rules" from "/etc/pf.custom"

# ----
block out proto tcp from any port <src_port> to any port 9092
```

With Linux version, use `iptables`:

```
sudo iptables -A INPUT -s 127.0.0.1 -p tcp --sport <src_port> -j REJECT --reject-with tcp-reset
```