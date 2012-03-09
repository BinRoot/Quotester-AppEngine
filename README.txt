API Notes:

web: /add
use: creating new accounts
arguments: ?targetName=[...]&password=[...]
output: new entity in Root/"root" key

web: /login
use: validate username-password combination
arguments: ?username=[...]&password=[...]
output: 1 or 0 depending on successful login

web: /post
use: post quote to a target
arguments: ?targetName=[...]&password=[...]&content=[...]&points=[...]&author=[...]
output: new entity in Quote/"[targetName]"

web: /quotes
use: list quotes by a target
arguments: ?targetName=[...]&password=[...]
output: JSON of quotes

web: /list
use: view all targets
arguments: N/A
output: list targetNames

web: /delete
use: delete a quote
arguments: ?targetName=[...]&password=[...]&name=[...]
output: 1 or 0 depending on successful deletion

 
 