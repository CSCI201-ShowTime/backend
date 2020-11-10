## Backend Integration
This `README` provides information to integrate front-end with backend. 
There will be minimum changes on HTML files to preserve front-end design. 
Instead, communication logic will be presented as separate JavaScript files. 
Despite the best effort, in the case where an HTML file must be changed, 
a change log will be attached to the bottom of this list.

#### Version
Backend: v0.4.0a3
- Fixed issue where tables using primitives contain NULL.
  Changed primitives to Boxed.

Front-end: 778736c

#### General Instructions
1. Import MySQL database from `showtime.sql` file.
2. Open Java project.
3. Add Maven support if needed.
4. Verify database port, username, and password in `src/main/resources/application.properties`.
5. HTML/ CSS/ Javascript files are included.
   To use another copy, replace the files in `src/main/resources/static`.
   (Warning: `src/main/resources/static/js/server` includes server-client communication logic.)

For detailed instructions, check instructions for Demo on 
[Confluence](https://201fptesting3.atlassian.net/wiki/spaces/DOC/pages/229779/Demo+Installation+Guide).

#### Version Specific Instructions
`login.html`
- Integrated `login-server.js` in `login.js`. 
  May separate at later date to decouple front-end and backend.
- Updated `AJAX` call in js.

`register.html`
- Updated `AJAX` call in js. May separate.

`account_page.html`
- Included `logout-server.js` and `logout-navbar-temp.js`.
  `logout-navbar-temp.js` should be integrated by front-end as fit.