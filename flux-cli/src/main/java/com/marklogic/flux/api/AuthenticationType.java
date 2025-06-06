/*
 * Copyright © 2024 MarkLogic Corporation. All Rights Reserved.
 */
package com.marklogic.flux.api;

public enum AuthenticationType {
    BASIC,
    DIGEST,
    CLOUD,
    KERBEROS,
    CERTIFICATE,
    /**
     * @since 1.2.0
     */
    OAUTH,
    SAML
}
