class FieldConf

  class << self
    def from(config, index)
      name = config['name']
      type = config['type']
      default = config['default']
      model = config['model']
      key = config['key']
      value = config['value']
      sources = config['sources']
      imports = config['imports']
      block = config['block']
      expression = config['expression']
      new(index, name, type, default, model, key, value, sources, imports, block, expression)
    end
  end

  attr_reader :index,
              :name,
              :store_name,
              :display_name,
              :type,
              :default,
              :model,
              :key,
              :value,
              :sources,
              :imports,
              :block,
              :expression

  def initialize(index, name, type, default, model, key, value, sources, imports, block, expression)
    if index.nil?
      raise ArgumentError, 'index is required on field'
    end
    @index = index
    @name, @store_name, @display_name = parse_names(name)
    @type, @required, @virtual, @hidden, @readonly, @transient, @increment_1 = parse_type_and_modifiers(type)
    @default = default.to_s unless default.nil?
    @model = model.to_s unless model.nil?
    @key = key.to_s unless key.nil?
    @value = value.to_s unless value.nil?
    @sources = parse_sources(sources)
    @imports = parse_imports(imports)
    @block = block.to_s unless block.nil?
    @expression = expression.to_s unless expression.nil?
  end

  def required?
    @required
  end

  def virtual?
    @virtual
  end

  def hidden?
    @hidden
  end

  def readonly?
    @readonly
  end

  def transient?
    @transient
  end

  def increment_1?
    @increment_1
  end

  # Returns whether the field should be stored in MongoDB
  #
  # @return [Boolean] true if the field should be stored in MongoDB,
  #         false otherwise
  def store_field?
    not virtual? and not readonly? and not transient?
  end

  private
  def parse_names(name_value)
    if name_value.nil?
      raise ArgumentError, 'name is required on field'
    end
    name, store_name, display_name = name_value.to_s.split(/\s+/)
    if store_name.nil?
      store_name = name
    end
    if display_name.nil?
      display_name = name
    end
    [name, store_name, display_name]
  end

  def parse_type_and_modifiers(type_and_modifiers)
    if type_and_modifiers.nil?
      raise ArgumentError, 'type is required on field'
    end
    modifiers = type_and_modifiers.to_s.split(/\s+/)
    type = modifiers[0]
    # modifiers default values
    required = false
    virtual = false
    hidden = false
    readonly = false
    transient = false
    increment_1 = false
    modifiers[1..].each do |modifier|
      case modifier
      when 'required'
        required = true
      when 'virtual'
        virtual = true
      when 'hidden'
        hidden = true
      when 'readonly'
        readonly = true
      when 'transient'
        transient = true
      when 'increment-1'
        increment_1 = true
      end
    end
    [type, required, virtual, hidden, readonly, transient, increment_1]
  end

  def parse_sources(sources)
    parse_string_array(sources, 'sources')
  end

  def parse_string_array(array, name)
    if array.nil?
      return []
    end
    unless array.is_a?(Array)
      raise ArgumentError, "#{name} must be an array on field"
    end
    array.map { |item| item.to_s }
  end

  def parse_imports(imports)
    parse_string_array(imports, 'imports')
  end

end
