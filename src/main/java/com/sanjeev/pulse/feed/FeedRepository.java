package com.sanjeev.pulse.feed;

import org.springframework.data.jpa.repository.JpaRepository;

interface FeedRepository extends JpaRepository<FeedItem, Long> {}
