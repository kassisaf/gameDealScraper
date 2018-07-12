gameDealScraper
===========

A full-stack web application designed to find and share free games.

![0.1.00-ALPHA Screenshot at small tablet resolution](docs/screenshots/preview-0.1.00-alpha-tablet.png?raw=true "0.1.00-ALPHA Screenshot at small tablet resolution")

## Installation:
Note: You will need a free Reddit API account to run your own instance.
- [Download the latest release](https://github.com/ababyduck/gameDealScraper/releases/latest) or clone the project
- Create `src/main/credentials.properties` and fill in your reddit API credentials (see `credentials.properties.example` for more info)
- Configure which port you want the app to run on in `src/main/application.properties` (default is 80)
- Run `mvn package` in the root folder to build the project (Requires [Apache Maven](https://maven.apache.org/download.cgi))
- Run `java -jar 'target\gamedealscraper-[version].jar'` (Requires [Java](https://java.com/en/download/))
- Access the app in a web browser at `http://localhost:80` (or whichever port you set it to run on)

## Planned features:

#### Initial release:
- [x] Finds "free to own" games from [/r/GameDeals](https://www.reddit.com/r/GameDeals/) and the [Humble Store](https://www.humblebundle.com/store)
- [X] Displays results in an attractive, responsive webpage
- [X] Easily deployable to a free VPS as a single .jar file

#### Future:
- [ ] Database for tracking deals as they are discovered or expire
- [ ] Admin login for manually editing or removing expired/problematic items (e.g. bad title parse)
- [ ] Automated reddit submission when new results are found
- [ ] Automated Discord messages when new results are found
- [ ] Support for additional sources including [Steam](https://store.steampowered.com/), [GOG](https://www.gog.com/), [Origin](https://www.origin.com/usa/en-us/store/browse?fq=platform:pc-download), and [UPlay](https://store.ubi.com/us/video-games/platforms/pc/?lang=en_US) storefronts.
- [ ] Discovery and tracking of new [chrono.gg](http://chrono.gg) Coinshop items
- [ ] Display game images, reviews and other information (Possibly via [IGDB](https://www.igdb.com/discover)?)
- [ ] Graphing of known discount history and historic lows, similar to [camelcamelcamel](https://www.camelcamelcamel.com/)