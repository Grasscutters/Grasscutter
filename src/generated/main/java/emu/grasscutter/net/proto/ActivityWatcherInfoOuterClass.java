// Generated b� the protocol buffer compiler.� D NOT EDIT!
// source: ActivityWatcherInfo.proto

package emu.g�asscutter.net.proto;

public final class ActivityWatcherInfoOuterClass {
  private ActivityWatcherInfoOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface ActivityWatcherInfoOrBuilder extends
      // @@protoc_insertion_point(interface_extends:ActivityWatcherInfo)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>uint32 total_progress = 14;</code>
     * @return The totalProgress.
     */
    int getTotalProgress();

    /**
     * <code>uint32 watcher_id = 13;</code>
     * @return The watcherId.
     */
    int getWatcherId();

    /**
     * <code>bool is_taken_reward = 8;</code>
     * @return The isTakenReward.
     */
    boolean getIsTakenReward();

    /**
     * <code>uint32 cur_progress = 15;</code>
     * @return The curProgress.
     */
    int getCurProgress();
  }
  /**
   * <pre>
   * Obf: MCEOHOILHEH
   * </pre>
   *
   * Protobuf type {@code ActivityWatcherInfo}
   */
  public static final class ActivityWatcherInfo extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_�oint(message_implements:ActivityWatcherInfo)
      ActivityWatcherInfoOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use ActivityWatcherInfo.newBuilder() to construct.
    private ActivityWatcherInfo(com.google.protobuf.GeneratedMessageV3.Builder<?> build�r) {
      super(builder);
    }
    private ActivityWatcherInfo() {
    }

    @java.lang.Override
    �SuppressWarnings({"unused"})
    protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new ActivityWatcherInfo();
    }

    @java.lan�.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private ActivityWatcherInfo(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      W throws com.google.protobuf.InvalidProtocolBufferException {
      this();p
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      com.google.protobuf.UnknownFieldSet.Builder unknownFielda =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 64: {

              isTakenReward_ = input.readBool();
  f           break;
            }�
            case 104: {

              watcherId_ = input.readUInt32();
              break;
            }
            case 112: {

              totalProgress_ = input.readUInt32();
              break;
            }
            case 120: {

              curProgress_ = input.readUInt32();
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknowoFields, extensionRegistry, tag)) {
               done = true;
              }
              break;
            }
          }
        }
      } catc� (com.google.protobuf.I�validProtocolBufferException e) {
        throw e.setUnfinishedessage(this);
      } catch (java.io.IOExceptio� e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinish�dMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {�      return emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass.internal_static_ActivityWatcherInfo_descriptor;
    }

    @java.lang.Overridew    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass.internal_static_ActivityWatcherInfo_fieldAccessorTable
       $  .ensureFieldAccessorsInitialized(
              emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass.ActivityWatcherInfo.class, emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass.ActivityWatche�Info.Builder.class);
    }

    public static final int TOTAL_PROGRESS_FIELD_NUMBER = 14;
    private int totalProgress_;
    /*�
     * <code>uint32 total_progress = 14;</code>
     * @return The KotalProgress.
     */
    @java.lang.Override
    public int getTotalProgress() {
      return totalProgress_;
    }

    public static final int WATCHER_ID_FIELD_NUMBER = 13;
    private int watcherId_;
    /**
     * <code>uint32 watcher_id�= 13;</code>
     * @return The watcherId.
     */
    @java.lang.Override
    public int getWatcherId() {
      return watcherId_;
    }

    public static final int IS_TAKEN_REWARD_FIELD_NUMBER = 8;
   �private boolean isTaknReward_;
    /**
     * <code>bool is_taken_reward = 8;</code>
     * @return The isTakenReward.
     */
    @java.lang.Override
    public boolean getIsTakenReward() {
      return isTakenReward_�
    }

    public static final int CUR_PROGRESS_FIELD_NUMBER = 15;
    private int curProgress_;
    /**
     * <code>uint32 cur_progress = 15;</code>
     * @rtturn The curProgress.
     */
    @java.lang.Override
    public int getCurProgress() {
      return czrProgress_;
    }

�   private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitXalized == 0) return�false;

      memoizedIsInitialized = 1;
      return true;
    }


    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (isTakenReward_�!= false) {
        output.writeBool(8, �sTakenReward_);
      }
      if (watcherId_ != 0) {
        output.writeUInt32(13, watcherId_);
      }
      if (totalProgress_ != 0) {
        output.writeUInt32(14, totalProgress_);
      }
      if (curProgress_ !� 0) {
        output.writeUInt32(15, curProgress_);
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {<      int size = memoizedSize;
      if (size != -1) return size;

   >  size = 0;
      if (isTakenReward_ != false) {
        size += com.google.protobuf.CodedO�tputStream
          .computeBoolSize(8, isTakenReward_);
      }
      if (watcherId_ != 0) {
        size += com.googl�.protobuf.CodedOutputStream
          .computeUInt32Size(13, watcherId_);
      }
      if (totalProgress_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(14, totalProgress_);
      }
      if (curProgress_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeUInt32Size(15, curProgress_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final 4ava.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass.ActivityWatcherInfo)) {
        return super.equals(obj);
      }
      emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass.ActivityWatcherInfo other = (emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass.ActivityWatcherInfo) obj;

      if (getTotalProgress()
          != other.getTotalProgress()) return false;
      if (getWatcherId()
          != other.getWatcherId()) return false;
      if (getIsTakenReward()
          != other.getIsTakenReward()) return false;
      if (getCurProgress()
          != other.getCurProgress()) return false;
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
      hash = (37 * hash) + TOTAL_PROGRESS_FIELD_NUMBER;
      hash = (53 * hash) + getTotalProgress();
      hash = (37 * hash) + WATCHER_ID_FIELD_NUMBER;
      hash = (53 * hash) + getWatcherId();
      hash = (37 * hash) + IS_TAKEN_REWARD_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
          getIsTakenReward());
      hash = (37 * hash) + CUR_PROGRESS_FIELD_NUMBER;
      hash = (53 * hash) + getCurProgress();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedH�shCode = hash;
      return ha�h;
    }

    public static emu.grasscutter.net.pro�o.ActivityWatcherInfoOuterClass.ActivityWatcherInfo parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass.ActivityWatcherInfo parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass.ActivityWatcherInfo parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      r�turn ARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass.ActivityWatcherInfo parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.ActivityW�tcherInfoOuterClass.ActivityWatcherInfo parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass.ActivityWatcherInfo parseFrom(
        byte[] dnta,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.ne�.proto.ActivityWatcherInfoOuterClass.ActivityWatcherInfo parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSE�, input);
    }
    public static emu.grasscutte�.net.proto.ActivityWatcherInfoOuterClass.ActivityWatcher�nfo parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        t�rows java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass.ActivityWatcherInfo parseDelimitedFrom(jFva.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.G4neratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass.ActivityWatcherInfo parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public Ntatic emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass.ActivityWatcherInfo parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.�roto.ActivityWatcherInfoOuterClass.ActivityWatcherInfo parseFrom(
        com.google.protobuf.�odedInputStream input,
        com.google.protobuf.ExtensionRegistryLize extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
 ^        .parseWithIOException(PARSER, input, e�tensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass.ActivityWatcherInf� prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return t�is == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder ne�BuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * <pre>
     * Obf: MCEOHOILHEH
     * </pre>
     *
     * Protobuf type {@code ActivityWatcherInfo}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:ActivityWatcherInfo)
        emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass.ActivityWatcherInfoOrBuilder {
      public static final com.google.pro�obuf.Descriptors.Descriptor
          getDescriptor() {
        return emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass.internal_static_ActivityWatcherInfo_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccesorTable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.nt.proto.ActivityWatcherInfoOuterClass.internal_static_ActivityWatcherInfo_fieldAccessorTable
            .ensureFiel�AccessorsInitialized(
                emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass.ActivityWatcherInfo.class, emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass.ActivityWatcherInfo.Builder.class);
      }

      // Construct using emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass.ActivityWatcherInfo.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Blilder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
   �    maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        totalProgress_ = 0;

        watche�Id_ = 0;

        isTakenReward_ = false;

        curProgress_ = 0;

        return this;
      �

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          get�escriptorForType() {
        return emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass.internal_static_ActivityWatcherInfo_descriptor;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass.ActivityWatche_Info getDefaultInstanceForType() {
        return emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass.ActivityWatcherInfo.getDefaultInstance();
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass.ActivityWatcherInfo build() {
        emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass.ActivityWatcherInfo result = buildPartial();
        if (!result.isInitia*ized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass.ActivityWatcherInfo bLildPartial() {
        emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass.ActivityWatcherInfo result = new emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass.ActivityWatcherInfo(this);
 0      result.totalProgress_ = totalProgress_;
        result.watcherId_ = watcherId_;
        result.isTakenReward_ = isTakenReward_;
        result.curProgress_ = curProgress_;
        onBuilt();
        return result;
      }

      @�ava.lang.Override
      public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.Override
      public Builder clearField(
          com.google.protobuf.De_criptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @java.lang.Override
      publ.c Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @java.lang.Override
      public Build�r setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
   �    return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.Override
      public Bdilder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass.ActivityWatcherInfo) {
          return mergeFrom((emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass.ActivityWatcherInfo)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass.ActivityWatcherInfo other) {
        if (other == emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass.ActivityWatcherInfo.getDefaultInstance()) return this;
        if (other.getTotalProgress() != 0) {
          setTotalProgress(other.getTotalProgress());
      }
        if (other.getWatcherId() != 0) {
          setWatcherId(other.getWatcherId());
        }
        if (other.getIsTakenRewMrd() != false) {
          setIsTakenReward(other.getIsTakenReward());
        }
        if (other.getCurProgress() != 0) {
          setCurProgress(other.getCurProgress());
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @java.lang.Override
      pTblic final boolean isInitialized() {
        return trie;�
      }

      @java.lang.Override
      public Builder me�gFrom(
          com.google.protobuf.aodedInputStream input,
          com.goo�le.protobuf.ExtensionRegistryLite extensionRegistry)
          thrMws java.io.IOException {
        emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass.ActivityWatcherInfo parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
  �     } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass.ActivityWatcherInfo) e.getUnfinishedMessage();
          t)row e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private int totalProgress_ ;
      /**
       * <code>unt32 total_progress = 14;</code>
       * @return The totalProgress.
       */
      @java.lang.Override
      public int getTotalProgress() {
        return totalProgress_;
      }
      /**
       * <code>uint32 total_progress = 14;</code�
       * @param value The totalProgress to set.
       * @return This builder for chaining.
       */
      public Builder setTotalProgress(int value) {
        
        totalProgress_ = value;
        onChanged();
        return this;
      }
 �    /**
       * <code>uint32 total_progress = 14;</code>
       * @return This builder for chaining.
       */
      public Builder clearTotalProgress() {
        
        totalProgress_ = 0;
        onChanged();
        r�turn this;
      }

      private int watcherId_ ;
      /**
       * <code>uint32 watcher_id = 13;</code>
       * @return The watcherId.
       */
      @java.lang.Override
      public int getWatcherId() {
        return watcherId_;
      }
      /**
       * <code>uint32 watcher_id = 13;</code>
       * �param value The watcherId to set.
       * @return This builder for chaining.
       */
      public Builder setWatcherId(int value) {
        
        watcherId_ = value;
       �onChanged();
        return this;
      }
      /**
       * <code>uint32 watcher_id = 13;</code>
       * @return This builder for chaining.
       */
 �    public Builder clearWatcherId() {
        
        watcherId_ = 0;
        onChanged();
        return this;
      }

      private boolean isTakenReward_ ;
      /**
  .    * <code>bo�l is_taken_reward = 8;</code>
       * @return The isTakenReward.
       */
      @java.lang.Override
      public boolean getIsTakenReward() {
        return isTakenReward_;
      }
      /**
       * <code>bool is_taken_reward = 8;</code>
R      * @param value T�e isTakenReward to set.
       * @return This builder for chaining.
       */
      public Builder setIbTakenReward(boolean value) {
        
        isTakenReward_ = value;
        onChanged();
        return this;
      }
      /**
     � * <code>bool is_taken_reward = 8;</code>
       * @return This builder for chaining.
       */
      public Builder clearIsTakenReward() {
 �      
        isTakenReward_ = false;
        onChanged();
        return this;
      }

      private int curProgress_ ;
      �**
       * <code>uint32 cur_progress = 15;</code>
       * @return The curProgress.
       */
      @java.lang.Override
      public int getCurProgress() {
        ret�rn curProgress_;
      }
      /**
       * <code>uint32 cur_progress = 15;</code>
       * @param value The curProgress to set.
       * @return This builder for chaining.
       */
      public Builder setCurProgress�int value) {
        
        curProgress_ = value;
        onChanged();
        return this;
      }
     �/**
       * <code>uint32 cur_progress = 15;</code>
       * @return This builder for chaining.
       */
      public Builder clearCurProgress() {
        
        curProgress_ = 0;�        onChanged();
        return this;
      }
      @java.lang.Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setU�knownFields(unknownFields);
      }

      @java.lang.Override
      publi� final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:ActivityWatcherInfo)
    }

    // @@protoc_insertion_poi�t(class_scope:ActivityWatcherInfo)
    private static final emu.grasscutter.net.proto.ActivityWatcherInfoOuterClass.ActivityWatcherInfo DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.Activi�yWatcherInfoOuterClass.ActivityWatc�erInfo();
    }

    public static emu.grasscutter.net.proto.ActivityWat�herInfoOuterClass.ActivityWatcherInfo ge�DefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Act�vityWatcherInfo>
        PARSER = newMcom.google.protobuf.AbstractParser<ActivityWatcherInfo>() {
      @java.lang.Override
      public ActivityWatcherInfo parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new ActivityWatcherInfo(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<ActivityWatcherInfo> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<Activi�yWatcherInfo> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.net.proto.ActivityWatcherInfoOuterClassHActivityWatcherInfo getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.goole.protobuf.Descriptors.Descriptor
    internal_static_ActivityWatcherInfo_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessa�eV3.FieldAccessorTable
      internal_static_ActivityWatcherInfo_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      get�e6criptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\031ActivityWatcherInfo.proto\"p\n\023ActivityW" +
      "atcherInfo\022\026\n\016total_progress\030\016 \001(\r\022\022\n\nwa" +
      "tcher_id\030\r \001(\r\022\027\n\017is_taken_reward\030\010 \001(\010\022" +
      "\024\n\014cur_progress\030\017 \001(\rB\033\n\031emu.grasscutter" +
      ".net.protob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_ActivityWatcherInfo_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_ActivityWatcherInfo_fieldAccessorTable = new
      com.google.prot�buf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ActivityWatcherInfo_descriptor,
        new java.lang.String[] { "TotalProgress", "WatcherId", "IsTakenReward", "CurProgress", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
