# Config repo

- **Native profile (e.g. Docker):** The config server uses a single search location (`file:/config-repo`) and does **not** resolve the `{application}` placeholder in paths. So it reads **flat** files at this root: `order-service.yml`, `user-service.yml`, etc. Those files are the source of truth for versions and overrides.
- **Folder layout:** The `order-service/`, `user-service/`, etc. folders and their `application.yml` files are kept for structure and for use with the **Git** backend (when `native` is not used). In native mode they are ignored; use the root-level `*-service.yml` files instead.
