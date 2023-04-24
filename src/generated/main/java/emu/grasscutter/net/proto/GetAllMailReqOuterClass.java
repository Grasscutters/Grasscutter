// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: GetAllMailReq.proto

package emu.grasscutter.net.proto;

public final class GetAllMailReqOuterClass {
    private GetAllMailReqOuterClass() {}

    public static void registerAllExtensions(com.google.protobuf.ExtensionRegistryLite registry) {}

    public static void registerAllExtensions(com.google.protobuf.ExtensionRegistry registry) {
        registerAllExtensions((com.google.protobuf.ExtensionRegistryLite) registry);
    }

    public interface GetAllMailReqOrBuilder
            extends
            // @@protoc_insertion_point(interface_extends:GetAllMailReq)
            com.google.protobuf.MessageOrBuilder {

        /**
         * <code>bool is_collected = 15;</code>
         *
         * @return The isCollected.
         */
        boolean getIsCollected();
    }
    /**
     *
     *
     * <pre>
     * CmdId: 1459
     * Name: OMGMJPFOPFD
     * </pre>
     *
     * Protobuf type {@code GetAllMailReq}
     */
    public static final class GetAllMailReq extends com.google.protobuf.GeneratedMessageV3
            implements
            // @@protoc_insertion_point(message_implements:GetAllMailReq)
            GetAllMailReqOrBuilder {
        private static final long serialVersionUID = 0L;
        // Use GetAllMailReq.newBuilder() to construct.
        private GetAllMailReq(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
            super(builder);
        }

        private GetAllMailReq() {}

        @java.lang.Override
        @SuppressWarnings({"unused"})
        protected java.lang.Object newInstance(UnusedPrivateParameter unused) {
            return new GetAllMailReq();
        }

        @java.lang.Override
        public final com.google.protobuf.UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private GetAllMailReq(
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
                        case 120:
                            {
                                isCollected_ = input.readBool();
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
            return emu.grasscutter.net.proto.GetAllMailReqOuterClass
                    .internal_static_GetAllMailReq_descriptor;
        }

        @java.lang.Override
        protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
                internalGetFieldAccessorTable() {
            return emu.grasscutter.net.proto.GetAllMailReqOuterClass
                    .internal_static_GetAllMailReq_fieldAccessorTable
                    .ensureFieldAccessorsInitialized(
                            emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq.class,
                            emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq.Builder.class);
        }

        public static final int IS_COLLECTED_FIELD_NUMBER = 15;
        private boolean isCollected_;
        /**
         * <code>bool is_collected = 15;</code>
         *
         * @return The isCollected.
         */
        @java.lang.Override
        public boolean getIsCollected() {
            return isCollected_;
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
            if (isCollected_ != false) {
                output.writeBool(15, isCollected_);
            }
            unknownFields.writeTo(output);
        }

        @java.lang.Override
        public int getSerializedSize() {
            int size = memoizedSize;
            if (size != -1) return size;

            size = 0;
            if (isCollected_ != false) {
                size += com.google.protobuf.CodedOutputStream.computeBoolSize(15, isCollected_);
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
            if (!(obj instanceof emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq)) {
                return super.equals(obj);
            }
            emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq other =
                    (emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq) obj;

            if (getIsCollected() != other.getIsCollected()) return false;
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
            hash = (37 * hash) + IS_COLLECTED_FIELD_NUMBER;
            hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(getIsCollected());
            hash = (29 * hash) + unknownFields.hashCode();
            memoizedHashCode = hash;
            return hash;
        }

        public static emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq parseFrom(
                java.nio.ByteBuffer data) throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq parseFrom(
                java.nio.ByteBuffer data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq parseFrom(
                com.google.protobuf.ByteString data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq parseFrom(
                com.google.protobuf.ByteString data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq parseFrom(
                byte[] data) throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq parseFrom(
                byte[] data, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq parseFrom(
                java.io.InputStream input) throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq parseFrom(
                java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3.parseWithIOException(
                    PARSER, input, extensionRegistry);
        }

        public static emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq
                parseDelimitedFrom(java.io.InputStream input) throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(PARSER, input);
        }

        public static emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq
                parseDelimitedFrom(
                        java.io.InputStream input, com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                        throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3.parseDelimitedWithIOException(
                    PARSER, input, extensionRegistry);
        }

        public static emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq parseFrom(
                com.google.protobuf.CodedInputStream input) throws java.io.IOException {
            return com.google.protobuf.GeneratedMessageV3.parseWithIOException(PARSER, input);
        }

        public static emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq parseFrom(
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
                emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq prototype) {
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
         * CmdId: 1459
         * Name: OMGMJPFOPFD
         * </pre>
         *
         * Protobuf type {@code GetAllMailReq}
         */
        public static final class Builder
                extends com.google.protobuf.GeneratedMessageV3.Builder<Builder>
                implements
                // @@protoc_insertion_point(builder_implements:GetAllMailReq)
                emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReqOrBuilder {
            public static final com.google.protobuf.Descriptors.Descriptor getDescriptor() {
                return emu.grasscutter.net.proto.GetAllMailReqOuterClass
                        .internal_static_GetAllMailReq_descriptor;
            }

            @java.lang.Override
            protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
                    internalGetFieldAccessorTable() {
                return emu.grasscutter.net.proto.GetAllMailReqOuterClass
                        .internal_static_GetAllMailReq_fieldAccessorTable
                        .ensureFieldAccessorsInitialized(
                                emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq.class,
                                emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq.Builder.class);
            }

            // Construct using
            // emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq.newBuilder()
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
                isCollected_ = false;

                return this;
            }

            @java.lang.Override
            public com.google.protobuf.Descriptors.Descriptor getDescriptorForType() {
                return emu.grasscutter.net.proto.GetAllMailReqOuterClass
                        .internal_static_GetAllMailReq_descriptor;
            }

            @java.lang.Override
            public emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq
                    getDefaultInstanceForType() {
                return emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq.getDefaultInstance();
            }

            @java.lang.Override
            public emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq build() {
                emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq result = buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            @java.lang.Override
            public emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq buildPartial() {
                emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq result =
                        new emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq(this);
                result.isCollected_ = isCollected_;
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
                if (other instanceof emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq) {
                    return mergeFrom((emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq) other);
                } else {
                    super.mergeFrom(other);
                    return this;
                }
            }

            public Builder mergeFrom(
                    emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq other) {
                if (other
                        == emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq.getDefaultInstance())
                    return this;
                if (other.getIsCollected() != false) {
                    setIsCollected(other.getIsCollected());
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
                emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                    parsedMessage =
                            (emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq)
                                    e.getUnfinishedMessage();
                    throw e.unwrapIOException();
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private boolean isCollected_;
            /**
             * <code>bool is_collected = 15;</code>
             *
             * @return The isCollected.
             */
            @java.lang.Override
            public boolean getIsCollected() {
                return isCollected_;
            }
            /**
             * <code>bool is_collected = 15;</code>
             *
             * @param value The isCollected to set.
             * @return This builder for chaining.
             */
            public Builder setIsCollected(boolean value) {

                isCollected_ = value;
                onChanged();
                return this;
            }
            /**
             * <code>bool is_collected = 15;</code>
             *
             * @return This builder for chaining.
             */
            public Builder clearIsCollected() {

                isCollected_ = false;
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

            // @@protoc_insertion_point(builder_scope:GetAllMailReq)
        }

        // @@protoc_insertion_point(class_scope:GetAllMailReq)
        private static final emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq
                DEFAULT_INSTANCE;

        static {
            DEFAULT_INSTANCE = new emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq();
        }

        public static emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq
                getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        private static final com.google.protobuf.Parser<GetAllMailReq> PARSER =
                new com.google.protobuf.AbstractParser<GetAllMailReq>() {
                    @java.lang.Override
                    public GetAllMailReq parsePartialFrom(
                            com.google.protobuf.CodedInputStream input,
                            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                            throws com.google.protobuf.InvalidProtocolBufferException {
                        return new GetAllMailReq(input, extensionRegistry);
                    }
                };

        public static com.google.protobuf.Parser<GetAllMailReq> parser() {
            return PARSER;
        }

        @java.lang.Override
        public com.google.protobuf.Parser<GetAllMailReq> getParserForType() {
            return PARSER;
        }

        @java.lang.Override
        public emu.grasscutter.net.proto.GetAllMailReqOuterClass.GetAllMailReq
                getDefaultInstanceForType() {
            return DEFAULT_INSTANCE;
        }
    }

    private static final com.google.protobuf.Descriptors.Descriptor
            internal_static_GetAllMailReq_descriptor;
    private static final com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
            internal_static_GetAllMailReq_fieldAccessorTable;

    public static com.google.protobuf.Descriptors.FileDescriptor getDescriptor() {
        return descriptor;
    }

    private static com.google.protobuf.Descriptors.FileDescriptor descriptor;

    static {
        java.lang.String[] descriptorData = {
            "\n\023GetAllMailReq.proto\"%\n\rGetAllMailReq\022\024"
                    + "\n\014is_collected\030\017 \001(\010B\033\n\031emu.grasscutter."
                    + "net.protob\006proto3"
        };
        descriptor =
                com.google.protobuf.Descriptors.FileDescriptor.internalBuildGeneratedFileFrom(
                        descriptorData, new com.google.protobuf.Descriptors.FileDescriptor[] {});
        internal_static_GetAllMailReq_descriptor = getDescriptor().getMessageTypes().get(0);
        internal_static_GetAllMailReq_fieldAccessorTable =
                new com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
                        internal_static_GetAllMailReq_descriptor,
                        new java.lang.String[] {
                            "IsCollected",
                        });
    }

    // @@protoc_insertion_point(outer_class_scope)
}
