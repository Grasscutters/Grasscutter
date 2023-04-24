// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: RoguelikeRuneRecord.proto

package emu.grasscutter.net.proto;

public final class RoguelikeRuneRecordOuterClass {
    private RoguelikeRuneRecordOuterClass() {}

    public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {}

    public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
        registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
    }

    public interface RoguelikeRuneRecordOrBuilder
            extends
            // @@protoc_insertion_point(interface_extends:RoguelikeRuneRecord)
            com.google.protobuf.MessageOrBuilder {

        /**
         * <code>uint32 FNOMMOAFCHG = 11;</code>
         *
         * @return The fNOMMOAFCHG.
         */
        int getFNOMMOAFCHG();

        /**
         * <code>uint32 rune_id = 10;</code>
         *
         * @return The runeId.
         */
        int getRuneId();

        /**
         * <code>uint32 EGIKJBIOOCF = 15;</code>
         *
         * @return The eGIKJBIOOCF.
         */
        int getEGIKJBIOOCF();
    }
    /**
     *
     *
     * <pre>
     * Name: KPMMLJKDLKH
     * </pre>
     *
     * Protobuf type {@code RoguelikeRuneRecord}
     */
    public static final class RoguelikeRuneRecord extends com.google.protobuf.GeneratedMessageV3
            implements
            // @@protoc_insertion_point(message_implements:RoguelikeRuneRecord)
            RoguelikeRuneRecordOrBuilder {
        private static final long serialVersionUID = 0L;
        // Use RoguelikeRuneRecord.newBuilder() to construct.
        private RoguelikeRuneRecord(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private RoguelikeRuneRecord() {}

        @java.lang.Override
        @SuppressWarnings({"unused"})
        protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
            return new RoguelikeRuneRecord();
        }

        @java.lang.Override
        public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private RoguelikeRuneRecord(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            this();
            if (extensionRegistry == null) {
                throw new java.lang.NullPointerException();
            }
            com.google.protobuf.UnknownFieldSet.Builder unknownFields =
                    com.google.protobuf.UnknownFieldSet.newBuilder();
            try {
                boolean done = false;
                while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 80:
                            {
                                runeId_ = input.readUInt32();
                                break;
                            }
                        case 88:
                            {
                                fNOMMOAFCHG_ = input.readUInt32();
                                break;
                            }
                        case 120:
                            {
                                eGIKJBIOOCF_ = input.readUInt32();
                                break;
                            }
                        default:
                            {
                                if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                    done = true;
                                }
                                break;
                            }
                    }
                }
            } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (java.io.IOException e) {
                throw new com.google.protobuf.InvalidProtocolBufferException(e).setUnfinishedMessage(this);
            } finally {
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
            }
        }

        public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
            return emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass
                    .internal_static_RoguelikeRuneRecord_descriptor;
        }

        @java.lang.Override
        protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
                internalGetFieldAccessorTable() {
            return emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass
                    .internal_static_RoguelikeRuneRecord_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(
                            emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord.class,
                            emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord.Builder
                                    .class);
        }

        public static final int FNOMMOAFCHG_FIELD_NUMBER = 11;
        private int fNOMMOAFCHG_;
        /**
         * <code>uint32 FNOMMOAFCHG = 11;</code>
         *
         * @return The fNOMMOAFCHG.
         */
        @java.lang.Override
        public int getFNOMMOAFCHG() {
            return fNOMMOAFCHG_;
        }

        public static final int RUNE_ID_FIELD_NUMBER = 10;
        private int runeId_;
        /**
         * <code>uint32 rune_id = 10;</code>
         *
         * @return The runeId.
         */
        @java.lang.Override
        public int getRuneId() {
            return runeId_;
        }

        public static final int EGIKJBIOOCF_FIELD_NUMBER = 15;
        private int eGIKJBIOOCF_;
        /**
         * <code>uint32 EGIKJBIOOCF = 15;</code>
         *
         * @return The eGIKJBIOOCF.
         */
        @java.lang.Override
        public int getEGIKJBIOOCF() {
            return eGIKJBIOOCF_;
        }

        private byte memoizedIsInitialized = -1;

        @java.lang.Override
        public final boolean isInitialized() {
            byte isInitialized = memoizedIsInitialized;
            if (isInitialized == 1) return true;
            if (isInitialized == 0) return false;

            memoizedIsInitialized = 1;
            return true;
        }

        @java.lang.Override
        public void writeTo(com.google.protobuf.CodedOutputStream output) throws java.io.IOException {
            if (runeId_ != 0) {
                output.writeUInt32(10, runeId_);
            }
            if (fNOMMOAFCHG_ != 0) {
                output.writeUInt32(11, fNOMMOAFCHG_);
            }
            if (eGIKJBIOOCF_ != 0) {
                output.writeUInt32(15, eGIKJBIOOCF_);
            }
            unknownFields.writeTo(output);
        }

        @java.lang.Override
        public int getSerializedSize() {
            int size = memoizedSize;
            if (size != -1) return size;

            size = 0;
            if (runeId_ != 0) {
                size += com.google.protobuf.CodedOutputStream.computeUInt32Size(10, runeId_);
            }
            if (fNOMMOAFCHG_ != 0) {
                size += com.google.protobuf.CodedOutputStream.computeUInt32Size(11, fNOMMOAFCHG_);
            }
            if (eGIKJBIOOCF_ != 0) {
                size += com.google.protobuf.CodedOutputStream.computeUInt32Size(15, eGIKJBIOOCF_);
            }
            size += unknownFields.getSerializedSize();
            memoizedSize = size;
            return size;
        }

        @java.lang.Override
        public boolean equals(final java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj
                    instanceof emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord)) {
                return super.equals(obj);
            }
            emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord other =
                    (emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord) obj;

            if (getFNOMMOAFCHG() != other.getFNOMMOAFCHG()) return false;
            if (getRuneId() != other.getRuneId()) return false;
            if (getEGIKJBIOOCF() != other.getEGIKJBIOOCF()) return false;
            if (!unknownFields.equals(other.unknownFields)) return false;
            return true;
        }

        @java.lang.Override
        public int hashCode() {
            if (memoizedHashCode != 0) {
                return memoizedHashCode;
            }
            int hash = 41;
            hash = (19 * hash) + getDescriptor().hashCode();
            hash = (37 * hash) + FNOMMOAFCHG_FIELD_NUMBER;
            hash = (53 * hash) + getFNOMMOAFCHG();
            hash = (37 * hash) + RUNE_ID_FIELD_NUMBER;
            hash = (53 * hash) + getRuneId();
            hash = (37 * hash) + EGIKJBIOOCF_FIELD_NUMBER;
            hash = (53 * hash) + getEGIKJBIOOCF();
            hash = (29 * hash) + unknownFields.hashCode();
            memoizedHashCode = hash;
            return hash;
        }

        public static emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord
                parseFrom(java.nio.ByteBuffer data)
                        throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord
                parseFrom(
                        java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                        throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord
                parseFrom(com.google.protobuf.ByteString data)
                        throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord
                parseFrom(
                        com.google.protobuf.ByteString data,
                        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                        throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord
                parseFrom(byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord
                parseFrom(byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                        throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord
                parseFrom(java.io.InputStream input) throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord
                parseFrom(
                        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                        throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
                    PARSER, input, extensionRegistry);
        }

        public static emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord
                parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord
                parseDelimitedFrom(
                        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                        throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
                    PARSER, input, extensionRegistry);
        }

        public static emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord
                parseFrom(com.google.protobuf.CodedInputStream input) throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord
                parseFrom(
                        com.google.protobuf.CodedInputStream input,
                        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                        throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
                    PARSER, input, extensionRegistry);
        }

        @java.lang.Override
        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(
                emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord prototype) {
            return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
        }

        @java.lang.Override
        public Builder toBuilder() {
            return this == DEFAULT_INSTANCE ? new Builder() : new Builder().mergeFrom(this);
        }

        @java.lang.Override
        protected Builder newBuilderForType(
                com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
            Builder builder = new Builder(parent);
            return builder;
        }
        /**
         *
         *
         * <pre>
         * Name: KPMMLJKDLKH
         * </pre>
         *
         * Protobuf type {@code RoguelikeRuneRecord}
         */
        public static final class Builder
                extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
                implements
                // @@protoc_insertion_point(builder_implements:RoguelikeRuneRecord)
                emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecordOrBuilder {
            public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
                return emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass
                        .internal_static_RoguelikeRuneRecord_descriptor;
            }

            @java.lang.Override
            protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
                    internalGetFieldAccessorTable() {
                return emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass
                        .internal_static_RoguelikeRuneRecord_fieldAccessorTable
                        .ensureFieldAccessorsInitialized(
                                emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord.class,
                                emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord.Builder
                                        .class);
            }

            // Construct using
            // emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord.newBuilder()
            private Builder() {
                maybeForceBuilderInitialization();
            }

            private Builder(com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
                super(parent);
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders) {}
            }

            @java.lang.Override
            public Builder clear() {
                super.clear();
                fNOMMOAFCHG_ = 0;

                runeId_ = 0;

                eGIKJBIOOCF_ = 0;

                return this;
            }

            @java.lang.Override
            public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
                return emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass
                        .internal_static_RoguelikeRuneRecord_descriptor;
            }

            @java.lang.Override
            public emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord
                    getDefaultInstanceForType() {
                return emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord
                        .getDefaultInstance();
            }

            @java.lang.Override
            public emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord build() {
                emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord result =
                        buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            @java.lang.Override
            public emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord
                    buildPartial() {
                emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord result =
                        new emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord(this);
                result.fNOMMOAFCHG_ = fNOMMOAFCHG_;
                result.runeId_ = runeId_;
                result.eGIKJBIOOCF_ = eGIKJBIOOCF_;
                onBuilt();
                return result;
            }

            @java.lang.Override
            public Builder clone() {
                return super.clone();
            }

            @java.lang.Override
            public Builder setField(
                    com.google.protobuf.Descriptors.FieldDescriptor field, java.lang.Object value) {
                return super.setField(field, value);
            }

            @java.lang.Override
            public Builder clearField(com.google.protobuf.Descriptors.FieldDescriptor field) {
                return super.clearField(field);
            }

            @java.lang.Override
            public Builder clearOneof(com.google.protobuf.Descriptors.OneofDescriptor oneof) {
                return super.clearOneof(oneof);
            }

            @java.lang.Override
            public Builder setRepeatedField(
                    com.google.protobuf.Descriptors.FieldDescriptor field,
                    int index,
                    java.lang.Object value) {
                return super.setRepeatedField(field, index, value);
            }

            @java.lang.Override
            public Builder addRepeatedField(
                    com.google.protobuf.Descriptors.FieldDescriptor field, java.lang.Object value) {
                return super.addRepeatedField(field, value);
            }

            @java.lang.Override
            public Builder mergeFrom(com.google.protobuf.Message other) {
                if (other
                        instanceof
                        emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord) {
                    return mergeFrom(
                            (emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord) other);
                } else {
                    super.mergeFrom(other);
                    return this;
                }
            }

            public Builder mergeFrom(
                    emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord other) {
                if (other
                        == emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord
                                .getDefaultInstance()) return this;
                if (other.getFNOMMOAFCHG() != 0) {
                    setFNOMMOAFCHG(other.getFNOMMOAFCHG());
                }
                if (other.getRuneId() != 0) {
                    setRuneId(other.getRuneId());
                }
                if (other.getEGIKJBIOOCF() != 0) {
                    setEGIKJBIOOCF(other.getEGIKJBIOOCF());
                }
                this.mergeUnknownFields(other.unknownFields);
                onChanged();
                return this;
            }

            @java.lang.Override
            public final boolean isInitialized() {
                return true;
            }

            @java.lang.Override
            public Builder mergeFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws java.io.IOException {
                emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord parsedMessage =
                        null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                    parsedMessage =
                            (emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord)
                                    e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private int fNOMMOAFCHG_;
            /**
             * <code>uint32 FNOMMOAFCHG = 11;</code>
             *
             * @return The fNOMMOAFCHG.
             */
            @java.lang.Override
            public int getFNOMMOAFCHG() {
                return fNOMMOAFCHG_;
            }
            /**
             * <code>uint32 FNOMMOAFCHG = 11;</code>
             *
             * @param value The fNOMMOAFCHG to set.
             * @return This builder for chaining.
             */
            public Builder setFNOMMOAFCHG(int value) {

                fNOMMOAFCHG_ = value;
                onChanged();
                return this;
            }
            /**
             * <code>uint32 FNOMMOAFCHG = 11;</code>
             *
             * @return This builder for chaining.
             */
            public Builder clearFNOMMOAFCHG() {

                fNOMMOAFCHG_ = 0;
                onChanged();
                return this;
            }

            private int runeId_;
            /**
             * <code>uint32 rune_id = 10;</code>
             *
             * @return The runeId.
             */
            @java.lang.Override
            public int getRuneId() {
                return runeId_;
            }
            /**
             * <code>uint32 rune_id = 10;</code>
             *
             * @param value The runeId to set.
             * @return This builder for chaining.
             */
            public Builder setRuneId(int value) {

                runeId_ = value;
                onChanged();
                return this;
            }
            /**
             * <code>uint32 rune_id = 10;</code>
             *
             * @return This builder for chaining.
             */
            public Builder clearRuneId() {

                runeId_ = 0;
                onChanged();
                return this;
            }

            private int eGIKJBIOOCF_;
            /**
             * <code>uint32 EGIKJBIOOCF = 15;</code>
             *
             * @return The eGIKJBIOOCF.
             */
            @java.lang.Override
            public int getEGIKJBIOOCF() {
                return eGIKJBIOOCF_;
            }
            /**
             * <code>uint32 EGIKJBIOOCF = 15;</code>
             *
             * @param value The eGIKJBIOOCF to set.
             * @return This builder for chaining.
             */
            public Builder setEGIKJBIOOCF(int value) {

                eGIKJBIOOCF_ = value;
                onChanged();
                return this;
            }
            /**
             * <code>uint32 EGIKJBIOOCF = 15;</code>
             *
             * @return This builder for chaining.
             */
            public Builder clearEGIKJBIOOCF() {

                eGIKJBIOOCF_ = 0;
                onChanged();
                return this;
            }

            @java.lang.Override
            public final Builder setUnknownFields(
                    final com.google.protobuf.UnknownFieldSet unknownFields) {
                return super.setUnknownFields(unknownFields);
            }

            @java.lang.Override
            public final Builder mergeUnknownFields(
                    final com.google.protobuf.UnknownFieldSet unknownFields) {
                return super.mergeUnknownFields(unknownFields);
            }

            // @@protoc_insertion_point(builder_scope:RoguelikeRuneRecord)
        }

        // @@protoc_insertion_point(class_scope:RoguelikeRuneRecord)
        private static final emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord
                DEFAULT_INSTANCE;

        static {
            DEFAULT_INSTANCE =
                    new emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord();
        }

        public static emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord
                getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        private static final com.google.protobuf.Parser<RoguelikeRuneRecord> PARSER =
                new com.google.protobuf.AbstractParser<RoguelikeRuneRecord>() {
                    @java.lang.Override
                    public RoguelikeRuneRecord parsePartialFrom(
                            com.google.protobuf.CodedInputStream input,
                            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                            throws com.google.protobuf.InvalidProtocolBufferException {
                        return new RoguelikeRuneRecord(input, extensionRegistry);
                    }
                };

        public static com.google.protobuf.Parser<RoguelikeRuneRecord> parser() {
            return PARSER;
        }

        @java.lang.Override
        public com.google.protobuf.Parser<RoguelikeRuneRecord> getParserForType() {
            return PARSER;
        }

        @java.lang.Override
        public emu.grasscutter.net.proto.RoguelikeRuneRecordOuterClass.RoguelikeRuneRecord
                getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    private static final com.google.protobuf.Descriptors.Descriptor
            internal_static_RoguelikeRuneRecord_descriptor;
    private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
            internal_static_RoguelikeRuneRecord_fieldAccessorTable;

    public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

    static {
        java.lang.String[] descriptorData = {
            "\n\031RoguelikeRuneRecord.proto\"P\n\023Roguelike"
                    + "RuneRecord\022\023\n\013FNOMMOAFCHG\030\013 \001(\r\022\017\n\007rune_"
                    + "id\030\n \001(\r\022\023\n\013EGIKJBIOOCF\030\017 \001(\rB\033\n\031emu.gra"
                    + "sscutter.net.protob\006proto3"
        };
        descriptor =
                com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
                        descriptorData, new com.google.protobuf.Descriptors.FileDescriptor[] {});
        internal_static_RoguelikeRuneRecord_descriptor = getDescriptor().getMessageTypes().get(0);
        internal_static_RoguelikeRuneRecord_fieldAccessorTable =
                new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
                        internal_static_RoguelikeRuneRecord_descriptor,
                        new java.lang.String[] {
                            "FNOMMOAFCHG", "RuneId", "EGIKJBIOOCF",
                        });
    }

    // @@protoc_insertion_point(outer_class_scope)
}