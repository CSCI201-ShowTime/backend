## Backend Integration
This `README` provides information to integrate front-end with backend. 
There will be minimum changes on HTML files to preserve front-end design. 
Instead, communication logic will be presented as separate JavaScript files. 
Despite the best effort, in the case where an HTML file must be changed, 
a change log will be attached to the bottom of this list.

#### Version
Backend: v0.6.2 TORTELLI
- Added polymorphic behavior when `event/POST`. Requires additional testing.

Known issues
- When creating a new `Event`, even when sent to a specific URI, 
  Jackson will auto deserialize the received `JSON` to a subclass based on its fields,
  and the `Repository` will save to the database according to the converted subclass.
  e.g. If the `JSON` contains fields in `Budget`, even when sent to `api/event/durationevent`,
  it will be saved to `Budget`.
- `event/POST` has undergone very basic testing.
- Security is temporarily disabled.

#### General Instructions
1. Import MySQL database from `showtime_wevent_20201118.sql` file.
2. Open Java project.
3. Add Maven support if needed.
4. Verify database port, username, and password in `src/main/resources/application.properties`.
5. Place front-end files in `src/main/resources/static`.
   (Warning: `src/main/resources/static/js/server` may include server-client communication files.)

For detailed instructions, check instructions for Demo on 
[Confluence](https://201fptesting3.atlassian.net/wiki/spaces/DOC/pages/229779/Demo+Installation+Guide).

#### Version Specific Instructions
- None.