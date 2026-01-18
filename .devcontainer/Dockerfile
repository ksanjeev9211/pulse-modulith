FROM eclipse-temurin:25-jdk

RUN apt-get update && apt-get install -y --no-install-recommends \
    bash \
    ca-certificates \
    curl \
    git \
    unzip \
    zip \
    openssh-client \
    procps \
    less \
    locales \
    maven \
    sudo \
  && rm -rf /var/lib/apt/lists/*

RUN sed -i 's/# en_US.UTF-8 UTF-8/en_US.UTF-8 UTF-8/' /etc/locale.gen \
  && locale-gen
ENV LANG=en_US.UTF-8 \
    LC_ALL=en_US.UTF-8

ARG USERNAME=dev
ARG USER_UID=1000
ARG USER_GID=1000

# Create group/user if needed; tolerate existing GID/UID 1000
RUN set -eux; \
    if ! getent group "${USER_GID}" >/dev/null; then \
      groupadd --gid "${USER_GID}" "${USERNAME}"; \
    fi; \
    if ! id -u "${USER_UID}" >/dev/null 2>&1; then \
      useradd --uid "${USER_UID}" --gid "${USER_GID}" -m -s /bin/bash "${USERNAME}"; \
    else \
      # UID exists; still ensure the named user exists
      if ! id -u "${USERNAME}" >/dev/null 2>&1; then \
        useradd -m -s /bin/bash "${USERNAME}"; \
      fi; \
    fi; \
    echo "${USERNAME} ALL=(ALL) NOPASSWD:ALL" > "/etc/sudoers.d/${USERNAME}"; \
    chmod 0440 "/etc/sudoers.d/${USERNAME}"

WORKDIR /workspaces
USER ${USERNAME}
