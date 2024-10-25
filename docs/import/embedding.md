---
layout: default
title: Adding embeddings
parent: Importing Data
nav_order: 7
---

Flux supports generating vector embeddings suitable for use in the new 
[vector query capabilities in MarkLogic 12](https://www.progress.com/blogs/introducing-hybrid-search-with-early-access-to-marklogic-server-12).

## Table of contents
{: .no_toc .text-delta }

- TOC
{:toc}

## Specifying the location of chunks

Defaults to what the splitter generates - i.e. "/chunks" for JSON and "/node()/chunks" for XML. 

We'll need CLI options to customize this:

- `--embedder-chunks-json-pointer /path/to/my/chunks`
- `--embedder-chunks-xml-xpath /ex:path[enabled='true']/chunks`
- `--embedder-chunks-xml-namespace ex="org:example"`

## Specifying the embedder to use

User specifies `--embedder-type` option. No default, as we won't ship with any embedder. Using this will trigger the feature. 

We can  have abbreviations of "azure" and "minilm".

Otherwise, value must be a class name.

Can have `--embedder-option key=value`. Or `-Ekey=value`.

Can have `--embedder-batch-size` to control how many chunks (not docs) are sent to the embedder in a single request. 

## Specifying where the embedding goes

We can default to what our splitter creates. Will override that via:

- `--embedder-embedding-json-pointer /path/to/embedding`
- `--embedder-embedding-xml-xpath /ex:path/to/embedding`
- `--embedder-embedding-xml-namespace ex=org:example`

Re: the XML namespace. Should we just have a single `--xpath-namespace` option that applies to all 3 of these xpath options?


