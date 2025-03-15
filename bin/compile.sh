#!/bin/bash
set -e

# Directories
PROTO_DIR="src"
OUT_KOTLIN="generated/kotlin"
OUT_TS="generated/ts"

# Create output directories if they don't exist
mkdir -p "${OUT_KOTLIN}"
mkdir -p "${OUT_TS}"

# Find all proto files (recursively)
PROTO_FILES=$(find "${PROTO_DIR}" -name "*.proto")

# Compile for Kotlin
echo "Compiling Protobufs for Kotlin..."
protoc -I="${PROTO_DIR}" \
    --plugin=protoc-gen-kotlin=protoc-gen-kotlin \
    --java_out="${OUT_KOTLIN}" \
    --kotlin_out="${OUT_KOTLIN}" \
    ${PROTO_FILES}

# Compile for TypeScript using ts-proto
echo "Compiling Protobufs for TypeScript..."
npx protoc -I="${PROTO_DIR}" \
    --ts_opt=long_type_number,generate_dependencies \
    --ts_out="${OUT_TS}" \
    ${PROTO_FILES}

npx barrelsby --directory ${OUT_TS} --delete

echo "Compilation finished!"
