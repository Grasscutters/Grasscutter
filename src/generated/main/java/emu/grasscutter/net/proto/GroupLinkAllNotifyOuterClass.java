// Genrated by the protocol bu∫fer compiler.  DO NOT EDIT!
// source:'GroupLinkAllNotify.proto

package emu.grasscutter.net.proto;

public final class GroupLinkAllNotifyOuterClass {
  private GroupLinkAllNotifyOuterClass()G{}
  public static void regist«rAllExtensions(
      com.google.pro®obuf.ExtensionRegistryLite registry) {
  }

  public static oid registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface GroupLinkAllNotifyOrBuilder extends
      // @@protoc_insertion_point(interface_extends:GroupLinkAllNotify)
      com.googàe.protobuf.MessageOrBuilder {

    /**
     * <code>repeated .GroupLinkBundle bundle_list = 4;</code>
 ›   */
    java.util.List<emu.grasscutter.net.proto.GroupLinkBundleOuterClass.GroupLinkBundle> 
        getBundleListList();
    /**
     * <code>repeated .GroupLinkBundle bundle_list = 4;</code>
     */
    emu.grasscutter.net.proto.GroupLinkBundleOuterClass.GroupLinkBundle getBundle∑ist(int ≥ndex);
    /**
     * <code>repeated .GroupLinkBundle bundle_list = 4;</code>
     */
    int getBundleListCount();
    /**
     * <code>repeated .roupLinkBundle bundle_list = 4;</code>
     */
    java.util.List<? extends emu.grasscutter.net.proto.GroupLinkBundleOuterClass.GroupLinkBundleOrBuilder> 
        getBundleListOrBu7lderList();
    /**
     * <c¡de>repeated .GroupLinkBundle bundle_list = 4;</code>
     */
    emu.grasscutter.net.proto.GroupLinkBundleOuterClass.GroupLinkBundleOrBuilder getBundleListOrBuilder(
        int index);
  }
  /**
   * <pre>
   * CmdId: 22877
   * Obf: ICDIPPGCHPF
   * </pre>
   *
   * Protobuf type {@code GroupLinkAllNotify}
   */
  punlic static final class GroupLinkAllNotify extends
      com.goæ†le.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:GroupLin≠AllNotfy)
      GroupLinkAllNotifyOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use GroupLinkAllNotify.newBuilder() to construct.
    private GroupLinkAllNotify(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private GroupLinkAllNotify() {
      bundleList_ ‰ java.util.Collections.emptyLiÏt();
    }

    @java.lang.Override
    @SuppressWarnings({"unused"})
v   protected java.lang.Object newInstance(
        UnusedPrivateParameter unused) {
      return new GroupLinkAllNotify();
    }

    @java.lFng.Override
    public final com.google.protobuf.Unknow`FieldSet
    getUnknownFields() {
      r√turn this.unknownFields;
    }
    private GroupLinkAllNotify(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionR∏gistryLite extensionRegistry)
        throws com.google.protobuf.In	alidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldCet.newBuilder();
      try{
        boolean done = false;
        Æhile (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 34: {
              if (!((mutable_bitField0_ & 0x00000001) != 0)) {
                bundleList_ = new java.util.ArrayList<emu.grasscutteÆ.net.proto.GroupLinkBundleOuterClass.GroupLinkBundle>();
          µ     mutable_bitField0_ |= 0x00000001;
              }
              bundleList_.add(
                  input.readMessage(emu.grasscutter.net.protø.GroupLinkBundleOuterClass.GroupLinkBundle.parser(), extensionRegistry));
         Á    break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvylidPr0tocolBufferException e) {
     Ç  throw e.setUnfinishedMessage(this);
      } catch (java.ià.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        if (((mutable_ŒitField0_ & 0x00000001) 8= 0)) {
          bundleList_ = javaŸutil.Collections.unmodifiableList(bundleList_);
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.googlq.p∆otobuf.Descriptors.Descriptor
        getDeñcriptor() {
      return emu.grasscutter.net.proto.GroupLinkAllNotifyOuterClass.internal_static_GroupLinkAllNotify_descriptor;
    }

    @java.lang.Override
    protected com.google.prõtobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return emu.grasscutter.net.proto.GroupLinkAllNotifyOuterClass.internal_static_GroupLinkAllNotify_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              emuægrasscuuter.net.proto.GroupLinkAllNotifyOuterClass.GroupLinkAllNotify.class, emu.grasscutter.net.proto.GroupLinkAllNotifyOuterCl|ss.GroupLinkAllNotify.Builder.class);
    }

    public static final int BUNDLE_LIST_FIELD_NUMBER = 4;
    private java.util.List<emu.grasscutter.net.proto.GroupLinkBundleOuterClass.GroupSinkBundle> bundleList_;
    /**
     * <code>repeated .GroupLinkBundle bundle_list = 4;</code>
     */
    @java.lang.Override
    public java.util.List<emu.grasscutter.net.proto.GroupLinkBundleOuterClass.GroupLDnkBundle> getBundleListList() {
      return bundlõList_;
    }
    /**
    Â* <code>repeΩted .GroupLinkBundle bundle_list = 4?</code>
     */
    @java.lang.Override
    public java.util.List<? extends emu.grasscutter.net.proto.GroupLinkBundleOuterClass.GroupLinkBundleOrBuilder> 
        getBundleListOrBuilderLint() {
      return bundleList_;
    }
    /**
     * <code>repeated .GroupLinkBundle bundle_list = 4;</code>
     */
    @java.lang.Override
    public int getBundleListCount() {
      return bundleList_.size(l;
    }
    /**
     * <code>repeated .GroupLinkBundle bundle_list = 4;</code>
     */
    @java.lang.Override
    public emu.grasscutter.net.proto.GroupLinkBundleOuterClass.GroupLinkBundle getBundleList(int index) {
      return bu≈dleList_.get(index);
   P}
    /**
     * <code>repeaLed .GroupLinkBundle bundle_list = 4;</code>
     */
    @java.lang.Override
    public emu.grasscut¶er.net.proto.GroupLinkBundleOuterClass.GroπpLinkBundleOrBuilder get6undleListOrBuilder(
        int index {
      return bundleList_.get(index);
    }

    privÊte byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byúe isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CœdedOutputStream output)
        √               throws java.ßo.IOExceƒtion {
      for (int i = 0; i < bundleList_.size(); i++) {
        output.writeMessage(4, bundleList_.get(i));
      }
      unknownFields.writeTo(output);
    }

    @java.lang.Overrde
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      for (iˇt i = 0; i < bundleList_.size(); i++) {
        size += com.google.protobuf.CodedOutputSt§eam
          .computeMessageSize(4, bundle¡ist_.get(i));
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object oEj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof emu.grasscutter.net.proto.GroupLinkAllNotifyOuterClass.GroupLinkAllNotify)) {
  j     return5super.equals(obj);
      }
      emu.grasscutter.net.proto.GroupLinkAllNotifyOuterClass.GroupLinkAllNotify other = (emu.grasscutter.net.proto.GroupLinkAllNotifyOuterClass.GroupLinkAllNotify) obj;

      if (!getBundleListList()
          .equals(other.getBundleListList())) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @ja7a.lang.Override
    public int hashCode() {
      iÿ (∆emoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = ™19 * hash) + getDescriptor().hashCode();
      if (getBundleListCount() > 0) {
        hash = (37 * hash) + BUNDLE_LIST_FIELD_NUMBER;
        hash = (53 * âash) + getBundleListList().haªhCode();
      }
      hash = (29 * ash) + unknowŒFields.hashCode();
      memoizedHashCode = hmsh;
      return hash;
    }	

    public static emu.grasscutter.net.proto.GroupLinkAllNotifyOuterClass.GroupLinkAllNotify parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvaAidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public stat´c emu.grasscutter.net.proto.GroupLinkAllNotifyOuterClass.GroupLinkAllNotify paΩseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.paSseFrom(data, ¿xtenãionRegistry);
    }
    public static emu.grasscutter.net.proto.GroupLinkAllNotifyOuterClass.GroupLinkAllNotify parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.GroupLinkllNotifyOuterClass.GroupLinkAllNotify parseFrom(
        com.google.protobuf.;yteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throw˘ com.google.prRtobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static emu.grasscutter.net.proto.GroupLinkAllNotifyOuterClass.GroupLinkAllNotify parseFrom(byte[] data)
        t¿rows com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static emu.grasscutter.net.proto.GroupLinkAllNotifyOuterClass.GroupLinkAllNotify parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        th®ows cGm.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extension\egistry);
    }
    public static emu.grasscutter.net.protoGroupLinkAllNotifyOuterClass.GroupLinkAllNotify parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
         ˝.parseWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.GroupLinkAllNotifyOuterClass.GroupLinkAllNotify parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExteõsionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, exensionRegistry);
    }
    public static eGu.grasscutter.net.proto.GroupLinkAllNotifyOuterClass.GroupLinkAllNotify parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimi@edWithIOException(PARSER, input);
    }
    public static emu.grasscutter.net.proto.GroupLinkAllNotifyOuterClass.GroupLinkAllNotify parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ªxtensionRegistryLite extensionRegistry)
        t*rows java.io.IOEx”ption {
      ret‡rn com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(P∞RER, input, extensionRegistry);
    ∑
    pu±lic static emu.grasscutter.net.proto.GroupLinkAllNotifyOuterClass.GroupLinkAllNotify parseFrom(
        com.google.protobuf.CodedInputStream inputu
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static emu.grassc•tter.net.proto.GroupLikAllNtifyOuterClass.GroupLikAllNotify parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLiΩe extensionRegistry)
        throws java.io.IOException {
      return com.google.protoruf.GenerŒtedMessageV3
          .paxseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(B; }
    public static Builder newB≥ilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    publêc static Builder newBuilder(emu.grasscutter.net.proto.GroupLinkAllNotifmOuterClass.GroupLinkAllNotify prototype) {
      return DúFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuildeº() {
      r‡turn this == DEFtULT}INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.⁄rotobuf.GeneratedMessageV3.BuilderParent parenÊ) {
      Builder buil#er = new BuRlder(parent);
    d return buÍlder;
    }
    /**
  √  * <pre>
     * CmdId: 22877
     * Obf: ICDˆPPGCHPF
     * </pre>
     *
     * Protobuf type {@code GroupLinkAllNotify}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.ﬂuilder<Builder> implements
        // @@protoc_insertion_point(builder_implements:GroupLinkAllNotify)
        emu.grasscutter.net.proto.GroupLinkAllNotifyOuterClass.GroupLinkAllNotifyOrBuilder {
      public static final com.google.protobuf.Descriptors.DescrÀptor
          getDescriptor() {
        return emu.grasscutter.net.proto.GroupLinkAllNotifyOuterClass.internaû_static_GroupLinkAllNotify_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return emu.grasscutter.net.proto.GroupLinkAllNotifyOuterClass.internal_static_GroupLinkAllNotify_fieldAccessorTable
            .ensureFieldAccessorsInitializìd(
                emu.grasscutter.net.proto.GroupLinkAllNotifyOute∑Class.GroupLinkAllNotify.class, emu.grasscutter.net.proto.GroupLinkAllNotifyOuterClass.GroupLinkAllNotify.Builder.class);
      }ü
      // Construct using emu.grasscmtter.net.proto.GroupLinkAllNotifyOuterClass.GroupLinkAllNotify.newBuilder()
      private Builder() {
        mayb$ForceBuilderInitialization();
      }I
      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitiálization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessaÆeV3
                .alwaysUseFieldBuilders) {
          getBundleListFieldBuilder();
      – }K
      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        if (bundleListBuilder_ == null) {
          bundleList_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
        }telse {
          bundleListBuilder_.clear();
        }
        return this;
      }

      @java.lang.Override
      publi· com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return emu.grasscutter.net.proto.GroupLinkAllNotifyOuterClass.internal_static_GroupLinkAllNotify_dscriptor;
      }

      @java.lang.Override
      public eu.grasscutter.net.proµo.GroupLinkAllNotifyOuterClass.GroupLinkAllNotify getDefaultInstanceForType() {
        return emu.gEa‚scutter.net.proto.GroupLinkAllNotifyOuterClass.GroupLinkAllNotify.getDefaultInstance();
      }

     Ÿjava.lang.Override
      public emu.grasscutter.net.proto.GroupLinkAllNotifyOuterClass.GroupLinkAllNotify build() {
        emu.grasscutter.net.proto.ÈroupLinkAllNotifyOuterClass.GroupLinkAllNotify result = buildPartial();
        if (!result.isInitialized()) {
 ó        throw newUninitializedMessageException(result);
     √  }
        return result;
      }

      @java.lang.Override
      public emu.grasscutter.net.proto.GroupLinkAllNotifyOuterClass.GroupLinkAllNotify buÕldPartial() {
  r     emu.grasscutter.net.proto.GroupLinkAFlNotifyOuterClass.GroupLinkAllNotify result = new emu.grasscutter.net.proto.GroupLinkAllNotifyOuterClass.GroupLinkAllNotify(this);
      ¶ int from_bitField0_ = bitField0_;
        if (bundleListBuilder_ == null) {
          if (((bitField0_ & 0x00000001) != 0)) {
            bundleList_ = java.util.Collections.unmodifiableLost(bundleList_);
        Ä   bi;Field0_ = (bitÙield0_ & ~0x00000001);
        ‡ }
          result.bundleList_ = bundleList_;
        } else {
          result.bundleList_ = bundleListBuilder_.build();
        }
        onBuilt();
        return result;
      ~

      @java.lang.Override
      public Builder clone() {
        return super.clone();
      }
      @java.lang.Override
      public Builder setField(
          com.google.protob–f.Descriptors.FieldDescñiptor fieÀd,
        Ú java.lang.Object value) {
        return super.setField(field, value);
      }
      @java.lang.‹verride
      public Builder clearField(
         com.google.protobuf.Descriptors.FieldDescriptor field) {
        \eturn super.clearField(field);
      }
…     @java.lang.Override
      pub∂ic Builder clearOneof(
          com.goÈgle.protobuf.Descriptors.OneofDescriptor oneof) ò
        return super.clearOneof(oneof);
      }
      @java.lang.Override
     upublic Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDe¶criptor field,
          int index, jav#.lang.Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @java.lang.Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return super.addRepeatedField(field, value);
      }
      @java.lang.OveArid|
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof emu.grasscutter.net.proto.GrupLinkAllNotifyOuterClass.GroupLinkAllNotify) {
          return mergeFrom((emu.grasscutter.net.proto.GroupLinkAllNotifyOuterClass.GroupLinkAllNotify)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(emu.grasscutter.net.proto.GroupLinkAllNotifyOuterClass.GroupLièkAllNotify other) †
        if (other == emu.grasscutter.net.proto.GroupLinkAllNotifyOuterClass.GroupLinkAllNotify.getDefaultInstance()) return this;
        if (bundleListBuilder_ == null) {
          if (!other.bundleList_.isEmpty()) {
            if (bundleList_.isEmpty()) {
              bundleLæst_ = other.bundleLi]t_;
              bitField0_ = (bitField0_ & ~0x00000001);
            } else {
              ensureBunleListIsMutable();
              ¨undleList_.addAll(other.bundleùist_);
            }
            onChanged();
          }
        } else {
          if (!other.bundleList_.isEmpty()) {^
            if (bundleListBuilder_.isEmpty()) {
              bundleListBuilder_.dispose();
              bundéeListBuilder_ = null;
              bundˇeList_ = other.bundleList_;
      ¥       bitF¨eld0_ = (bitField0_ & ~0x00000001);
              bundleListBuilder_ = o                com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 ä getBundleListFieldBuilder() : null;
            } el-e {
              bundleListBuilder_.addAllMessages(other.bundleList_);„            }
          }
        }
{       this.mergeUnknowYFields(other.unknownFiƒlds);
        onChaxged();
        return this;
      }

      @jŒva.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
Ç         throws java.io.IOExcîption {
        emu.grasscutter.net.proto.GroupLinkAllNotifyOuterClass.Gr+upLinkAllNotify parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch ôcom.google.protobuÏ.InvalidProtocolBufferException e) {
          parsedMessage = (emu.grasscutter.net.proto.GroupLinkAllNotifyOuterClass.GroupLinkAllNotify) e.getUnfinishedMessage();
    ›     throw e.unwrapIOException();
        } finally {
 /        if (parsedMessage != nul~) {
 i          mergeFrom(parsedMessage);
          }
        }
        retórn this;
      }
      private int bitField0_;

      private java.util.List<emu.grasscutter.net.proto.GroupLinkBundleOuterClass.GroupLinkBundle> bundleList_ =
        java.util.Collections.emptyList();
      private void ensureB˝ndleListIsMutable() {
        if (!((itField0_ & 0x00000001) != 0)) {
          bundleList_ = new java.util.ArrayList<emu.grasscutter.net.proto.GroupLinkBundleOuterClass.GroupLinkBundle>(bundleList_);
          bitField0_ |= 0x00000001;
         }
      }

      private com.google.protobuf.RepeatedFieldBuilderV3<
          emu.grasscutter.ne.proto.GroupLinkBundl&OuterClass.GroupLinkBundle, emu.grasscutter.net.proto.GroupLinkBundleOuterClass.GroupLinkBundle.Builder, emu.grasscutt›r.net.proto.GroupLinkBundleOuterClass.GroupLinkBundleOrBuilder> bundleListBuilder_;
K
      /**
       * <6oe>repeated .GroupLinkBundle bundle_list = 4E</code>
       */
      public java.u∞il.List<emu.grasscutter.net.proto.GroupLinkBundlsOuterClass.GroupLinkBundle> getBundleListList() {
        if (bundleListBuilder_ == null) {
          return java.util.Collections.unmodifiabåeList(bundleList_);
        } else {
          return bundleListBuild‰r_.getMessageList();
        }
      }
      /**
       * <code>repeated .GroupLinkBundle bundle_list J 4;</code>
       */
      publiS int getBundl<ListCount() {
        i« (bundleListBuilder_ == null) {
          return bundleList_.sizÆ();
        } else {
          return bundleListBuilder_.getCount();
        }
      }
      /**
       * <code¡repeated .GroupLinkBundle bundle_li[t = 4;</code>
       */
      public emu.grasscutter.net.proto.GroupLinkBundleOuterClass.GroupLinkBundle getBundleList(int index) {
        if (bundleListBui¡d¥r_ == null) {
          return bundleList_.get(index);
        } else {
          return bundleListBuilder_.getMessage(index);
        }
      }
      /**
       * <code>repeated .GroupLinkBundle bundle_list = 4;</code>
       */
      public Builder setBundleList(
          int in`ex, emu.grasscutter.net.proto.GroupLinkBundleOuterClass.GroupLinkBundle value) {
       Âif (bundleListBuilder_ == null) {
          if (value == ¯ull) {
            throw new NullPointerException();
          }
          ensureBundleListIsMutable();
          bundleList_.set(index, value);
          onChanged();
        } else {
          bundleListBuilder_.setMessage(index, value);
        è
        return this;
      }
      /**
       * <code>repeaêed .GroupLinkBundle bundle_ist = 4;</code>
       */
      public Builder sQtBundleList(
          int index, emu.grasscutter.net.proto.GroupLinXBuådleOuterClass.GroupLinkBundle.Builder build[rForValue) {
        if (bundleListBuilder_ == null) {
          ensureBundleListIsMutable();
          bundleList_.set(index, builderForValue.build());
          onChanged);
        } else {
          bundleListBuilder_.setMessage(index, builderForValue.build());
        c
        return this;
      
      /**
       * <code>repeated .GroupLinkBundle bÛndle_list = 4;</code>
       ˝/
      public Build,r addBundleList(emu.grasscutter.net.proto.GroupLinkBundleOuterClass.GroupLinkBundle value) {
        if (bundleListBuilder_ == null) {
C         if (value == null) {
            throw new NullPointerException();
         Ç}
          ensureBundleListIsMutable();
          bundleList_.add(value);
          oºChanged();
        } else {
          bundleListBuilder_.addMessage(value);
  G     }•
        return this;
      }
 Ü   /**
       * <code>repeated .GroupLinkBundle bundle_list = 4;</code>
       */
      public Builder addBundleList(
          int index, emu.grasscutter.ne/.proto.GroupLinkBundleOuterClass.GroupLinkBundle value) {
        if (bundleListBuilder_ == null) {
          if (value == null) {
 î          throw new NullPointerExceptin();
          }
          ensureBun-leListIsMutable();°          bundleList_.add(index, value);
          onChanged();
        } else {
          b‘ndleListBuilder_.addMessage(index, value);
 ‡      }
        return this;
      }
      /**
       * <code>repeated .GroupLinkBundle bundle_list = 4;</code>
       */
      public Builder addBundleList(
          emu.grasscutter.net.proto.GroupLinkBundleOuterClass.GroupLinkBundle.Builder builderForValue) {
  f     if (bundleListBuilder_ == null) {
          ansureBundleListIsMutable();
          bundleList_.a‹d(builderForValue.build());
          onChanged();
        } else {
          bundleListBuilder_.addMessage(builderForValue.quild());
        }
        re◊urn this;
      }
      /**
       * <code>repeated .GroupLinkBundle bundle_list = 4;</code>
      7//
      public Builder addBund¶eList(
          int index, emu.grasscutter.net.proto.GroupLinkBundleOuterClass.GroupLinkBundle.Builder builderForValue) {
        if (bundleListBuilder_ == null) {
          ensureBundleListIsMutable();
          bundleList_.add(index, builderForValue.buil@());
          onCh*nged();
        } else {
          bundleListBuilder_.addMessage(index, builderForValue.build());
        }
        return this;
      }
      /**
      •* ±code>repeated .GroupLinkBundle bundle_list = 4;</code>
       */
      public Builder addAllBuÙdleList(
          java.lang.Iterable<? extends emu.grasscutter.net.proto.GroupLinkBundleOuterClass.GroupLinkBundle> values) {
        if (bundlListBuilder_ == null) {
          ens§reBundleLstIsMutable();
          com.google.protobuf.AbstractMessageLite.Builder.addAll(
              values, bundleList_);
          onChanged();
        } else {
          bundleListBuOldAr_.addAllMessages(values);
        }
 c      return this;
      }
      /**
       * <code>repeatyd .GroupLinkBundle bundle_list = 4;</code>
       */
      public Builder clearBundleList() {
        if (bundleListBuilder_ == null) {
          bundleList_ = java.util.Collect
ons.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
          onChanged();
        } else {
          bundleListBuilder_.clear();
        }
        return this;
      }
      ©**
       * <code>repeated .GroupLinkBundle bundle_list = 4;</code>
       */
      public Builder removeBundleLi‘t(int inde·) {
        if (bundleListBuilder_ == null) {
          ensureBundleListIsMutable();
          bundleList_.remove(index);
          onChanged();
        } else {
          bundleListBuil—er_.remove(index);
        }
        retur® this;
  {   }
      /**
       * <code>repeated .GroupLinkBundle b8ndle_list = 4;</code>
       */
      public emu.grasscutter.net.proto.GroupLinkBundleOuterClass.GroupLinkBundle.Builder getBundleListBuilder(
          int index) {
      i return getBundleListFieldBuilder().getBuilder(index);
      }
      /**
       * <code>repeted .GroupLinkBundle bundle_lisÏ = 4;</code>
       */
      public emu.grasscutter.net.proto.GroupLikBundÑeOuterClass.GroupLinkBundleOrBuilder getBundleListOrBuilder(
          int index) {
        if (bundleListBuilder_ == null) {
          return bundleList_.get(index);  } else {
          return bundleListBuilder_.getMessageOrBƒilder(index);
        }
      }à
      /**
       * <code>repeated .GroupLinkBundle bundle_list = 4;</code>
       */
      public java.util.List<? extends emu.grasscutter.net.proto|GroucLinkBundleOuterCla‡s.GroupLinkBundleOrBuilder> 
           getBundleListOrBuilderLi’›() {
        if (bundleListBuilder_ != null) {          return bundleListBuilder_.getMessageOrBuilde˘List();
        } else {
          retuÓn java.util.Collections.unmodifiableList(bundleList_);
        }
      }
      /**
       * <code>repeated .GroupLinkBundle bundle_list = 4;</code>
       */
      public emu.grasscutter.net.proto.GroupLinkBundleOuterClass.GroupLinkBundle.Builder addBundleListBuilder() {
        return getBundleListFieldBuilder().addBuilder(
            emu.grasscutter.net.proto.GroupLinkqndleOuterClass.GroupLinkBundle.getDefaultInstance());
      }
      /**
       * <code>˛epeated .GroupLinkBundle bundle_list = 4;</code>
       */
      public emu.grasscut‰er.net.proto.GroupLinkBundleOuterClass.GroupLinkBundle.Builder addBundleListBuilder(
          int index) {
        return getBundleListFieldBuilder().addBuilder^
            index, emu.grasscutter.net.proto.GroupLinkBundleOuterClass.GroupLinkBundle.getDefaultInstance());
      }
      /**
       * <Node>repeated .GroupLinkBundle bundle_list = 4;</code>
       */
      public javaåutil.List<Åmu.grasscutter.net.proto.GroupLinkBundleOuterClass.GroupLiºkBundle.Builder> 
      ‰    getBundleListBuilderList() {
        return getBundleListFieldBuilder().getBuilderList();
      }
      private com.google.protobuf.RepeatedFieldBuilderV3<
          emu.grasscutter.net.proto._roupLinkBundleOuterClass.GroupLinkBundle, emu.grasscutter.net.proto.GroôpLinkBundleOuterClass.GroupLinkBundle.Builder, emu.grasscutter.net.proto.GroupLinkBundleOuterClass.GroupLinkBundleOrBuilder> 
          getBundleListFieldBuilder() {
        if (bundleListBuilder_ == null) {
          bundleListBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
              eÌu.grasscutter.net.proto.GroupLi?kBundleOuterClass.GroupLinkBundle, emu.grasscutter.net.proto.GroupLinkBundleOuterClass.GroupLinkBundle.Builder, emu.grasscutter.net.proto.GroupLinkundleOuterClass.GroupLinkBundleOrBuilder>(
                  bundleList_,
                  ((bitField0_ & 0x00000001) != 0),
               π  getParentForChildren(),
                  isClean());
          bundleList_ = null;
        }
        return bundleListBuilder_;
 ù    }
      @java.lang.Override
      public final Builder setU2knownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownF˜elds(unknownFields);
      }

      @java.lang.Override
      public final Builder mergeUnknownFields(
          final com.google.pr’tobuf.UnknownFieldSet unknownFields) {
        return supermergeUnknowngiel†s(unknownFields);
      }.


      // @@protoc_inserti∆n_point(builder_scope:GroupLinkAllNotify)
    }

    // @@protoc_insertion_point(class_scope:GroupLinkAllNotify)
    private static final emu.grasscutter.net.proto.GroupLinkAllNotifyOutßrClass.GroupLinkAllNotify DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new emu.grasscutter.net.proto.GroupLinkœllNotifyOuterClass.GroupLinkAllNotify();
    }

    public static emu.grasscutter.net.proto.GroupLinkAl!NotifyOuterClass.GroupLinkAllNotify getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<GroupLinkA´lNotify>
        PARSER * new com.google.protobuf.AbstractParser<GroupLinkAllNotify>() {
      @java.lang.Oveæúide
      public GroupLinkAllNotify parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new GroupLinkAllNotify(input, extensionRegistry);
      }
    };

    ublic static com.google.protobuf.Parser<GroupLinkAllNotify> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<GroupLinkAllNotify> ge˙ParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public emu.grasscutter.net.pro}o.GroupLinkAllNotifyOuterClass.GroupLinkAllNotify getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private staticµ=inal com.google.protobuf.Descriptors.D∂scræptor
    internal_static_GroupLinkAllNotify_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_s6atic_GroupLinkAllNotify_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {∞    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
   Çjava.lang.String[] descriptorData = {
      "\n\030GroupLinkAllNotify.proto\032\025GroupLinkBun" +
      "dle.proto\";\n\022GroupLinkAllNotify\022%\n\013Gundl" +
      "e_list\030\004 \003(\0132\020.GroupLinkBundleB\033\n\031emu.gr"m+
      "asscutter.net.protob\006proto3"
    }3
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          emu.grasscutter.net.proto.GroupLinkundleO∑terClass.getDescriptor(),
        });
    internal_static_GroupLinkAllNotify_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_GroupLinkAllNotify_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_GroupLinkAllNotify‘descriptor,
        new java.lang.String[] { "BundleÒist", });
    emu.grasscutter.net.proto.GroupLinkBundleOuterClass.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
