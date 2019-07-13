# DeadDNSPostbox

With this tiny program you can transform DNS requests to pushover messages.
DNS requests are mostly free and can pass the firewall from captive portal protected networks like public WLAN networks.

## Dependencies

- https://github.com/sps/pushover4j
- valid pushover.net account

## Test

You can check this program under windows with: nslookup MESSAGE localhost

## Important notice

This solution is only a proof on concept and not for productive enviroments.
The message is not encrypted and every hop through the final destination can read this message.